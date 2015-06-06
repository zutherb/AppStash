package main

import (
	"net/http"

	"log"

	"github.com/emicklei/go-restful"
	"github.com/zutherb/productservice/repository"
)

type ProductService struct {
	ProductRepository repository.ProductRepository
}

func (p ProductService) Register() {
	ws := new(restful.WebService)
	ws.Consumes(restful.MIME_JSON).Produces(restful.MIME_JSON)
	ws.Route(ws.GET("/all").To(p.findAllProducts).
		// docs
		Doc("get all products").
		Operation("findAllProducts").
		Returns(200, "OK", []repository.Product{}))

	restful.Add(ws)
}

func (p ProductService) findAllProducts(request *restful.Request, response *restful.Response) {
	response.WriteEntity(p.ProductRepository.FindAllProducts())
}

func main() {

	p := ProductService{ProductRepository: repository.NewProductRepository(repository.NewSession())}

	p.Register()

	log.Printf("start listening on localhost:18080")
	log.Fatal(http.ListenAndServe(":18080", nil))
}
