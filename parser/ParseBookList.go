package parser

import (
	"crawler/engine"
	"regexp"
)

/*
*
<a href="https://book.douban.com/subject/36104107/" title="长安的荔枝"
*/
const regexpBookList = `<a href="([^"]+)" title="([^"]+)"`

func ParseBookList(content []byte) *engine.TaskResult {
	allSubMatch := regexp.MustCompile(regexpBookList).FindAllSubmatch(content, -1)
	result := engine.TaskResult{}

	for _, m := range allSubMatch {
		bookName := string(m[len(m)-1])
		result.Item = append(result.Item, m[len(m)-1])
		result.Tasks = append(result.Tasks, engine.Task{
			Url: string(m[1]),
			ParseFunc: func(c []byte) *engine.TaskResult {
				return ParseBookDetail(c, bookName)
			},
		})
	}

	return &result
}
