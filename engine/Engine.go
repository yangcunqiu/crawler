package engine

import (
	"crawler/fetcher"
	"log"
	"regexp"
	"time"
)

type Engine struct {
	BaseUrl string
	Tasks   []Task
}

func DefaultEngine(url string, parseFunc ParseFunc) *Engine {
	engine := Engine{BaseUrl: url}
	engine.Tasks = append(engine.Tasks, Task{
		Url:       url,
		ParseFunc: parseFunc,
	})
	return &engine
}

func (e *Engine) Run() {
	log.Println("开始爬取")
	for len(e.Tasks) > 0 {
		task := e.Tasks[0]
		if task.ParseFunc != nil {
			time.Sleep(2 * time.Second)
			content, err := fetcher.Fetch(task.Url)
			if err != nil {
				log.Printf("获取内容失败, url: %s, err: %v \n", task.Url, err)
				e.Tasks = e.Tasks[1:]
				continue
			}

			taskResult := task.ParseFunc(content)
			log.Printf("解析成功, url: %s, item: %s \n 生成后续任务: %v \n", task.Url, taskResult.Item, taskResult.Tasks)
			if len(taskResult.Tasks) > 0 {
				e.Tasks = append(e.Tasks, taskResult.Tasks...)
			}
		}
		e.Tasks = e.Tasks[1:]
	}
	log.Printf("爬取完成")
}

func DefaultParse(regexpStr string, urlPrefix string, parseFunc ParseFunc) func(content []byte) *TaskResult {
	return func(content []byte) *TaskResult {
		allSubMatch := regexp.MustCompile(regexpStr).FindAllSubmatch(content, -1)
		result := TaskResult{}

		for _, m := range allSubMatch {
			result.Item = append(result.Item, m[len(m)-1])
			result.Tasks = append(result.Tasks, Task{
				Url:       urlPrefix + string(m[1]),
				ParseFunc: parseFunc,
			})
		}

		return &result
	}
}
