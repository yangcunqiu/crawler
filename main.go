package main

import (
	"crawler/engine"
	"crawler/engine/scheduler"
	"crawler/parser"
)

func main() {
	// e := engine.DefaultEngine("https://book.douban.com", parser.ParseIndex)
	// e := engine.DefaultEngine("https://book.douban.com/tag/当代文学", parser.ParseBookList)
	e := engine.DefaultConcurrentEngine("https://book.douban.com", parser.ParseIndex)
	e.SetScheduler(&scheduler.SimpleScheduler{})
	e.Run()
}
