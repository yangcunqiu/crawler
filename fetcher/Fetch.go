package fetcher

type Fetch interface {
	Fetch(string) ([]byte, error)
	ConfigWaitTime()
}
