package repository

import "gopkg.in/mgo.v2/bson"

type Product struct {
	Id          bson.ObjectId 	`bson:"_id" json:"id"`
	ArticleId   string  		`bson:"articleId" json:"articleId"`
	Name        string  		`bson:"name" json:"name"`
	Urlname     string  		`bson:"urlname" json:"urlname"`
	Description string  		`bson:"description" json:"description"`
	ProductType string  		`bson:"type" json:"productType"`
	Price       float64 		`bson:"price" json:"price"`
}
