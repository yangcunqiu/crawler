package manage

import "log"

type SaveManage struct {
}

func (m *SaveManage) Dispose() chan any {
	out := make(chan any)

	go func() {
		var itemCount int
		for {
			item := <-out
			log.Printf("saveManage got: [%d], item: %s \n", itemCount, item)
			itemCount++
		}
	}()

	return out
}
