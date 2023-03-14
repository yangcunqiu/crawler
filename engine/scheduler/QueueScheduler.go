package scheduler

import (
	"crawler/engine"
	"log"
)

type QueueScheduler struct {
	taskChan   chan engine.Task
	workerChan chan chan engine.Task
}

func (s *QueueScheduler) WorkChan() chan engine.Task {
	return make(chan engine.Task)
}

func (s *QueueScheduler) Submit(task engine.Task) {
	s.taskChan <- task
}

func (s *QueueScheduler) Run() {
	log.Println("QueueScheduler start")
	s.taskChan = make(chan engine.Task)
	s.workerChan = make(chan chan engine.Task)
	go func() {
		var taskQueue []engine.Task
		var workQueue []chan engine.Task
		for {
			var activeTask engine.Task
			var activeWork chan engine.Task
			if len(taskQueue) > 0 && len(workQueue) > 0 {
				activeTask = taskQueue[0]
				activeWork = workQueue[0]
			}
			select {
			case t := <-s.taskChan:
				taskQueue = append(taskQueue, t)
			case w := <-s.workerChan:
				workQueue = append(workQueue, w)
			case activeWork <- activeTask:
				taskQueue = taskQueue[1:]
				workQueue = workQueue[1:]
			}
		}
	}()
}

func (s *QueueScheduler) WorkReady(taskChan chan engine.Task) {
	s.workerChan <- taskChan
}
