package main

import (
	"net/http"

	"log"
	"os"

	"github.com/emicklei/go-restful"
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

const (
	DATABASE_NAME   = "shop"
	COLLECTION_NAME = "product"
)

type Product struct {
	Id          bson.ObjectId `bson:"_id" json:"id"`
	ArticleId   string  `bson:"articleId" json:"articleId"`
	Name        string  `bson:"name" json:"name"`
	Urlname     string  `bson:"urlname" json:"urlname"`
	Description string  `bson:"description" json:"description"`
	ProductType string  `bson:"type" json:"productType"`
	Price       float64 `bson:"price" json:"price"`
}

type ProductService struct {
	Session *mgo.Session
}

func (p ProductService) Register() {
	ws := new(restful.WebService)
	ws.Consumes(restful.MIME_JSON).Produces(restful.MIME_JSON)
	ws.Route(ws.GET("/all").To(p.findAllProducts).
		// docs
		Doc("get all products").
		Operation("findAllProducts").
		Returns(200, "OK", []Product{}))

	restful.Add(ws)
}

func (p ProductService) findAllProducts(request *restful.Request, response *restful.Response) {
	result := []Product{}

	c := p.Session.DB(DATABASE_NAME).C(COLLECTION_NAME)
	c.Find(bson.M{}).All(&result)

	response.WriteEntity(result)
}

func main() {

	mongodb_host := os.Getenv("MONGODB_PORT_27017_TCP_ADDR")

	if mongodb_host == "" {
		mongodb_host = "mongodb-node"
	}

	session, err := mgo.Dial(mongodb_host)
	if err != nil {
		panic(err)
	}
	defer session.Close()

	p := ProductService{Session: session}

	p.Register()

	log.Printf("start listening on localhost:18080")
	log.Fatal(http.ListenAndServe(":18080", nil))
}
