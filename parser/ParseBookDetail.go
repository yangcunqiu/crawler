package parser

import (
	"crawler/engine"
	"crawler/model/third"
	"regexp"
)

/**
<div id="info" class="">
    <span>
      <span class="pl"> 作者</span>:
            <a class="" href="/author/4561353">刘慈欣</a>
    </span><br>
    <span class="pl">出版社:</span>
      <a href="https://book.douban.com/press/2162">重庆出版社</a>
    <br>
    <span class="pl">出品方:</span>
      <a href="https://book.douban.com/producers/63">科幻世界</a>
    <br>
    <span class="pl">副标题:</span> 死神永生<br>
    <span class="pl">出版年:</span> 2010-11<br>
    <span class="pl">页数:</span> 513<br>
    <span class="pl">定价:</span> 38.00元<br>
    <span class="pl">装帧:</span> 平装<br>
    <span class="pl">丛书:</span>&nbsp;<a href="https://book.douban.com/series/38">科幻世界·中国科幻基石丛书</a><br>
      <span class="pl">ISBN:</span> 9787229030933<br>
</div>
*/

const (
	regexpAuthor     = `<span class="pl"> 作者</span>:[\d\D]+<a class="" href="[^"]+">([^"]+)</a>`
	regexpPress      = `<span class="pl">出版社:</span>[\d\D]*?<a.*?>([^<]+)</a>`
	regexpProducer   = `<span class="pl">出品方:</span>[\d\D]*?<a.*?>([^<]+)</a>`
	regexpSubhead    = `<span class="pl">副标题:</span>([^"]+)<br/>`
	regexpImpD       = `<span class="pl">出版年:</span>([^"]+)<br/>`
	regexpPages      = `<span class="pl">页数:</span>([^"]+)<br/>`
	regexpPrice      = `<span class="pl">定价:</span>([^"]+)<br/>`
	regexpDecoration = `<span class="pl">装帧:</span>([^"]+)<br/>`
	regexpSeries     = `<span class="pl">丛书:</span>&nbsp;<a href="[^<]+">([^"]+)</a><br/>`
)

func ParseBookDetail(content []byte, bookName string) *engine.TaskResult {
	bookDetail := third.BookDetail{
		Title:      bookName,
		Author:     parseDetail(regexpAuthor, content),
		Press:      parseDetail(regexpPress, content),
		Producer:   parseDetail(regexpProducer, content),
		Subhead:    parseDetail(regexpSubhead, content),
		ImpD:       parseDetail(regexpImpD, content),
		Pages:      parseDetail(regexpPages, content),
		Price:      parseDetail(regexpPrice, content),
		Decoration: parseDetail(regexpDecoration, content),
		Series:     parseDetail(regexpSeries, content),
	}

	result := engine.TaskResult{}
	result.Item = append(result.Item, bookDetail)

	return &result
}

func parseDetail(regexpStr string, content []byte) string {
	subMatch := regexp.MustCompile(regexpStr).FindSubmatch(content)
	if len(subMatch) > 0 {
		return string(subMatch[len(subMatch)-1])
	} else {
		return ""
	}
}
