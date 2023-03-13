package utils

import (
	"bufio"
	"io"
	"os"
)

func GetStringLineByFile(fileAddr string) []string {
	file, err := os.OpenFile(fileAddr, os.O_RDONLY, 0666)
	if err != nil {
		return nil
	}

	defer func() {
		_ = file.Close()
	}()

	reader := bufio.NewReader(file)
	var result []string
	for {
		line, _, err := reader.ReadLine()
		if err == io.EOF {
			break
		}
		result = append(result, string(line))
	}

	return result
}
