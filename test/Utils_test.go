package test

import (
	"crawler/utils"
	"fmt"
	"math/rand"
	"testing"
	"time"
)

func TestReadFile(t *testing.T) {
	addr := "../proxy_info"
	file := utils.GetStringLineByFile(addr)
	fmt.Printf("%s", file)
}

func TestRandom(t *testing.T) {
	rand.Seed(time.Now().Unix())
	fmt.Println(rand.Intn(10))
}

func TestProxyHttpClient(t *testing.T) {
	client := utils.GetHttpProxyClient()
	fmt.Println(client)
}
