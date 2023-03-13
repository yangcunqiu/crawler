package engine

type Scheduler interface {
	submit(Task)
	configureWorkChan(chan Task)
}

type SimpleScheduler struct {
	workerChan chan Task
}

func (s *SimpleScheduler) submit(task Task) {
	go func() {
		s.workerChan <- task
	}()
}

func (s *SimpleScheduler) configureWorkChan(taskChan chan Task) {
	s.workerChan = taskChan
}
