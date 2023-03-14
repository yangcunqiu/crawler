package engine

type Scheduler interface {
	Submit(Task)
	WorkChan() chan Task
	Run()
	WorkReady(chan Task)
}
