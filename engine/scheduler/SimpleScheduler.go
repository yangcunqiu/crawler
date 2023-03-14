package scheduler

import (
	"crawler/engine"
	"log"
)

type SimpleScheduler struct {
	workerChan chan engine.Task
}

func (s *SimpleScheduler) Submit(task engine.Task) {
	go func() {
		s.workerChan <- task
	}()
}

func (s *SimpleScheduler) WorkChan() chan engine.Task {
	return s.workerChan
}

func (s *SimpleScheduler) Run() {
	log.Println("SimpleScheduler start")
	s.workerChan = make(chan engine.Task)
}

func (s *SimpleScheduler) WorkReady(tasks chan engine.Task) {
	return
}
