package main

import (
	"fmt"
	"os"
	"os/exec"
)

func post(url string, body string) {
	tmpfile, err := os.CreateTemp("", "request_body_*.json")
	if err != nil {
		fmt.Println("Error creating temporary file:", err)
		return
	}
	defer os.Remove(tmpfile.Name())

	_, err = tmpfile.WriteString(body)
	if err != nil {
		fmt.Println("Error writing to temporary file:", err)
		return
	}

	tmpfile.Close()

	curlCmd := exec.Command("curl", "-s", "-w",
		"\nHTTP Status Code: %{http_code}\n"+
			"Effective URL: %{url_effective}\n"+
			"Total Time: %{time_total}s\n"+
			"Request Time: %{time_connect}s\n"+
			"Redirect Time: %{time_redirect}s\n"+
			"Size Upload: %{size_upload} bytes\n"+
			"Size Download: %{size_download} bytes\n"+
			"Speed Download: %{speed_download} bytes/s\n"+
			"Speed Upload: %{speed_upload} bytes/s\n\n%{json}\n",
		"-X", "POST", "-d", "@"+tmpfile.Name(), "-H", "Content-Type: application/json", url)

	output, err := curlCmd.CombinedOutput()
	if err != nil {
		fmt.Println("Error executing curl command:", err)
		return
	}

	fmt.Println("Response:")
	fmt.Println(string(output))
}

func get(url string) {

}

func put(url string, body string) {

}

func delete(url string) {

}
