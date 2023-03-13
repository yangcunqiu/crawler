package parser

import (
	"crawler/engine"
)

const regexpTag = `<a href="(/tag[^"]+)">([^"]+)</a>`

func ParseTag(content []byte) *engine.TaskResult {
	return engine.DefaultParse(regexpTag, "https://book.douban.com", ParseBookList)(content)
}
