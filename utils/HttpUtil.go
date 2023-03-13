package utils

import (
	"github.com/go-resty/resty/v2"
	"log"
	"math/rand"
	"strings"
	"time"
)

func GetNewHttpClientWithProxy(proxyAddr string) *resty.Client {
	client := resty.New()
	proxyAddr = "https://" + proxyAddr
	log.Println("proxy addr: ", proxyAddr)
	client.SetProxy(proxyAddr)
	// client.SetBaseURL("https://book.douban.com")
	return client
}

func GetHttpProxyClient() *resty.Client {
	addr := "proxy_info"
	proxyList := GetStringLineByFile(addr)
	rand.Seed(time.Now().Unix())
	index := rand.Intn(len(proxyList))
	return GetNewHttpClientWithProxy(strings.TrimSpace(proxyList[index]))
}

func GetHttpClient() *resty.Client {
	return resty.New()
}
