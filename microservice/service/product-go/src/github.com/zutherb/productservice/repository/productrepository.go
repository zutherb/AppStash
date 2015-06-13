package repository

import (
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

const (
	DATABASE_NAME   = "shop"
	COLLECTION_NAME = "product"
)

type ProductRepository struct {
	Session *mgo.Session
}

func NewProductRepository(s *mgo.Session) ProductRepository {
	repository := ProductRepository{
		Session: s,
	}
	return repository
}

func (p ProductRepository) FindAllProducts() []Product {
	result := []Product{}

	c := p.Session.DB(DATABASE_NAME).C(COLLECTION_NAME)
	c.Find(bson.M{}).All(&result)

	return result
}

func (p ProductRepository) CountAllProducts() int {
	results := p.FindAllProducts()
	return len(results)
}

func (p ProductRepository) Save(product Product) error {
	c := p.Session.DB(DATABASE_NAME).C(COLLECTION_NAME)
	return c.Insert(product)
}
