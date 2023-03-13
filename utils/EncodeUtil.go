package utils

import (
	"bytes"
	"golang.org/x/net/html/charset"
	"golang.org/x/text/transform"
	"io"
)

// TransformUTF8 将任意编码数据转换为utf8编码
func TransformUTF8(content []byte) ([]byte, error) {
	encoding, _, _ := charset.DetermineEncoding(content, "")
	reader := transform.NewReader(bytes.NewReader(content), encoding.NewDecoder())
	return io.ReadAll(reader)
}
