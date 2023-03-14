package engine

import (
	"crawler/fetcher"
	"errors"
	"log"
)

type ConcurrentEngine struct {
	baseUrl   string
	task      Task
	workCount int
	in        chan Task
	out       chan *TaskResult
	Scheduler Scheduler
}

func DefaultConcurrentEngine(url string, parseFunc ParseFunc) *ConcurrentEngine {
	e := &ConcurrentEngine{
		baseUrl: url,
		task: Task{
			Url:       url,
			ParseFunc: parseFunc,
		},
		workCount: 100,
		in:        make(chan Task),
		out:       make(chan *TaskResult),
	}
	return e
}

func (e *ConcurrentEngine) SetScheduler(s Scheduler) {
	e.Scheduler = s
}

func (e *ConcurrentEngine) Run() {
	e.Scheduler.Run()

	for i := 0; i < e.workCount; i++ {
		go createWorker(e.Scheduler, e.out)
	}
	e.Scheduler.Submit(e.task)

	for {
		taskResult := <-e.out
		log.Printf("解析成功, url: %s, item: %s \n", e.task.Url, taskResult.Item)
		for _, t := range taskResult.Tasks {
			e.Scheduler.Submit(t)
		}
	}
}

func createWorker(s Scheduler, out chan *TaskResult) {
	in := s.WorkChan()
	for {
		s.WorkReady(in)
		task := <-in
		taskResult, err := work(task)

		if err != nil {
			continue
		}
		out <- taskResult
	}
}

func work(task Task) (*TaskResult, error) {
	if task.ParseFunc == nil {
		return nil, errors.New("parseFunc not nil")
	}

	content, err := fetcher.Fetch(task.Url)
	if err != nil {
		log.Printf("获取内容失败, url: %s, err: %v \n", task.Url, err)
		return nil, err
	}

	taskResult := task.ParseFunc(content)
	return taskResult, nil
}
