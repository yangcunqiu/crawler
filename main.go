package main

import (
	"crawler/engine"
	"crawler/engine/scheduler"
	"crawler/fetcher"
	"crawler/parser"
	"time"
)

func main() {
	// e := engine.DefaultEngine("https://book.douban.com", parser.ParseIndex)
	// e := engine.DefaultEngine("https://book.douban.com/tag/当代文学", parser.ParseBookList)
	e := engine.DefaultConcurrentEngine("https://book.douban.com", parser.ParseIndex)
	e.SetScheduler(&scheduler.QueueScheduler{})
	e.SetFetch(&fetcher.SimpleFetch{WaitTime: 1000 * time.Millisecond})
	e.Run()
}
