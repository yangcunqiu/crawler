package third

type BookDetail struct {
	Title      string `json:"title,omitempty"`
	Author     string `json:"author,omitempty"`
	Press      string `json:"press,omitempty"`
	Producer   string `json:"producer,omitempty"`
	Subhead    string `json:"subhead,omitempty"`
	ImpD       string `json:"impD,omitempty"`
	Pages      string `json:"pages,omitempty"`
	Price      string `json:"price,omitempty"`
	Decoration string `json:"decoration,omitempty"`
	Series     string `json:"series,omitempty"`
}

func (bd BookDetail) String() string {
	return "标题: " + bd.Title + ", " +
		"作者: " + bd.Author + ", " +
		"出版社: " + bd.Press + ", " +
		"出品方: " + bd.Producer + ", " +
		"副标题: " + bd.Subhead + ", " +
		"出版年: " + bd.ImpD + ", " +
		"页数: " + bd.Pages + ", " +
		"定价: " + bd.Price + ", " +
		"装帧: " + bd.Decoration + ", " +
		"丛书: " + bd.Series
}
