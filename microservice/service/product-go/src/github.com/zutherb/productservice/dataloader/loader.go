package dataloader

import (
	"encoding/csv"
	"os"
	"log"
	"strconv"

	"github.com/zutherb/productservice/repository"
	"gopkg.in/mgo.v2/bson"
)

type ProductDataloader struct {
	ProductRepository repository.ProductRepository
}

func NewProductDataloader(repository repository.ProductRepository) ProductDataloader {
	return ProductDataloader{
		ProductRepository: repository,
	}
}

func (d ProductDataloader) InitDatabaseIfNeeded() {
	if d.ProductRepository.CountAllProducts() == 0 {
		log.Println("Data inserted")
		insertProductData(d)
	} else {
		log.Println("No Data inserted")
	}

}

func insertProductData(d ProductDataloader) {
	rawData := readCsvRawData()
	for _, row := range rawData {
		price, err := strconv.ParseFloat(row[4], 64)

		if err != nil {
			log.Panic(err)
			os.Exit(1)
		}

		product := repository.Product{
			Id: bson.NewObjectId(),
			ArticleId:   row[0],
			ProductType: row[1],
			Name:        row[2],
			Description: row[3],
			Price:       price,
		}

		err = d.ProductRepository.Save(product)
		if err != nil {
			log.Fatal(err)
		}
	}
}

func readCsvRawData() [][]string {
	csvfile, err := os.Open("resources/product.csv")

	if err != nil {
		log.Panic(err)
		os.Exit(1)
	}

//	defer csvfile.Close()

	reader := csv.NewReader(csvfile)
	reader.Comma = ';'
	reader.FieldsPerRecord = -1

	rawCSVdata, err := reader.ReadAll()

	if err != nil {
		log.Panic(err)
		os.Exit(1)
	}

	return rawCSVdata
}
