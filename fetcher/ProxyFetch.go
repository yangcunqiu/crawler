package fetcher

import (
	"crawler/utils"
	"errors"
	"fmt"
	"log"
	"net/http"
	"time"
)

type ProxyFetch struct {
	WaitTime time.Duration
	waitChan <-chan time.Time
}

func (f *ProxyFetch) Fetch(url string) ([]byte, error) {
	if f.waitChan != nil {
		<-f.waitChan
	}
	client := utils.GetHttpProxyClient()
	resp, err := client.R().
		SetHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36").
		SetHeader("Accept", "text/event-stream").
		Get(url)

	if err != nil {
		log.Println("fetch 请求失败 ", err)
		return []byte{}, err
	}
	if resp.StatusCode() != http.StatusOK {
		log.Println("fetch 请求失败 ", resp.StatusCode())
		return []byte{}, errors.New(fmt.Sprintf("%v:\n%s", resp.StatusCode(), string(resp.Body())))
	}

	return utils.TransformUTF8(resp.Body())
}

func (f *ProxyFetch) ConfigWaitTime() {
	if f.WaitTime != 0 {
		f.waitChan = make(<-chan time.Time)
		f.waitChan = time.Tick(f.WaitTime)
	}
}
