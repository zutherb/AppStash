package main

import (
	"net/http"

	"log"

	"github.com/emicklei/go-restful"
	"github.com/zutherb/productservice/repository"
	"github.com/zutherb/productservice/dataloader"
	"gopkg.in/mgo.v2"
	"flag"
	"os"
)

type ProductService struct {
	ProductRepository repository.ProductRepository
}


type ApplicationContext struct {
	Session *mgo.Session
	ProductRepository repository.ProductRepository
	ProductService ProductService
	Dataloader dataloader.ProductDataloader
}

func (c ApplicationContext) Init() {
	c.Dataloader.InitDatabaseIfNeeded()
	c.ProductService.Register()
}

func (c ApplicationContext) Close() {
	c.Session.Close()
}

func createApplicationContext() ApplicationContext {
	session := repository.NewSession()
	productRepository := repository.NewProductRepository(session)

	return ApplicationContext{
		Session: session,
		ProductRepository: productRepository,
		Dataloader: dataloader.NewProductDataloader(productRepository),
		ProductService: ProductService{
			ProductRepository: productRepository,
		},
	}
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


var (
	mongoDbDebug bool
)

func init() {
	flag.BoolVar(&mongoDbDebug, "debug", false, "enables mongodb debug log")
}

func main() {
	flag.Parse()

	enableMongoDbDebugIfEnabled ()

	context := createApplicationContext()

	defer context.Close()

	context.Init()

	log.Printf("start listening on localhost:18080")
	log.Fatal(http.ListenAndServe(":18080", nil))
}

func enableMongoDbDebugIfEnabled () {
	if mongoDbDebug {
		mgo.SetDebug(true)

		var aLogger *log.Logger
		aLogger = log.New(os.Stderr, "", log.LstdFlags)
		mgo.SetLogger(aLogger)
	}
}
