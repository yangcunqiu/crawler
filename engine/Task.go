package engine

type ParseFunc func([]byte) *TaskResult

type TaskResult struct {
	Tasks []Task
	Item  []any
}

type Task struct {
	Url       string
	ParseFunc ParseFunc
}
