package main

import (
	"crawler/engine"
	"crawler/parser"
)

func main() {
	e := engine.DefaultEngine("https://book.douban.com", parser.ParseIndex)
	e.Run()
}
