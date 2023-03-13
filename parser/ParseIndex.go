package parser

import (
	"crawler/engine"
)

/*
*
<a class="" href="/tag/?view=type&amp;icn=index-sorttags-all">所有热门标签»</a>
*/
const regexpIndex = `<a class="" href="(/tag/)`

// const regexpIndex = `<a class="" href="([^"]+)?view=type&amp;icn=index-sorttags-all"\r?\n|(?<!\n)\r>([^"]+)»</a>`

func ParseIndex(content []byte) *engine.TaskResult {
	return engine.DefaultParse(regexpIndex, "https://book.douban.com", ParseTag)(content)
}
