package fetcher

import (
	"crawler/utils"
	"errors"
	"fmt"
	"log"
	"net/http"
	"time"
)

var waitChan = time.Tick(1000 * time.Millisecond)

// Fetch 获取url内容
func Fetch(url string) ([]byte, error) {
	<-waitChan
	client := utils.GetHttpClient()
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
