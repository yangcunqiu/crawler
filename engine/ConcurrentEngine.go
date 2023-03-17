package engine

import (
	"crawler/fetcher"
	"crawler/manage"
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
	Fetch     fetcher.Fetch
	ItemChan  chan any
	Manage    manage.Manage
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

func (e *ConcurrentEngine) SetFetch(f fetcher.Fetch) {
	e.Fetch = f
}

func (e *ConcurrentEngine) SetManage(m manage.Manage) {
	e.Manage = m
}

func (e *ConcurrentEngine) Run() {
	if e.Scheduler == nil {
		panic("Scheduler not nil")
	}
	if e.Fetch == nil {
		panic("Fetch not nil")
	}
	e.Scheduler.Run()
	e.Fetch.ConfigWaitTime()
	e.ItemChan = make(chan any)
	e.ItemChan = e.Manage.Dispose()

	for i := 0; i < e.workCount; i++ {
		go createWorker(e.Scheduler, e.out, e.Fetch)
	}
	e.Scheduler.Submit(e.task)

	for {
		taskResult := <-e.out
		go func() {
			for _, item := range taskResult.Item {
				e.ItemChan <- item
			}
		}()
		for _, t := range taskResult.Tasks {
			e.Scheduler.Submit(t)
		}
	}
}

func createWorker(s Scheduler, out chan *TaskResult, fetch fetcher.Fetch) {
	in := s.WorkChan()
	for {
		s.WorkReady(in)
		task := <-in
		taskResult, err := work(task, fetch)

		if err != nil {
			continue
		}
		out <- taskResult
	}
}

func work(task Task, fetch fetcher.Fetch) (*TaskResult, error) {
	if task.ParseFunc == nil {
		return nil, errors.New("parseFunc not nil")
	}

	content, err := fetch.Fetch(task.Url)
	if err != nil {
		log.Printf("获取内容失败, url: %s, err: %v \n", task.Url, err)
		return nil, err
	}

	taskResult := task.ParseFunc(content)
	return taskResult, nil
}
