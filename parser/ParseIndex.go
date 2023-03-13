package parser

import (
	"crawler/engine"
)

const regexpIndex = `<a class="" href="([^"]+)?view=type&amp;icn=index-sorttags-all"[\d\D]+>([^"]+)»</a>`

func ParseIndex(content []byte) *engine.TaskResult {
	return engine.DefaultParse(regexpIndex, "https://book.douban.com", ParseTag)(content)
}
