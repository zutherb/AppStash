package repository

import (
	"gopkg.in/mgo.v2"
	"os"
	"log"
	"time"
)

func NewSession() *mgo.Session {
	mongodb_host := os.Getenv("MONGODB_PORT_27017_TCP_ADDR")

	if mongodb_host == "" {
		mongodb_host = "mongodb-node"
	}

	log.Printf("Connect to MongoDB on %s", mongodb_host)

	session, err := mgo.DialWithTimeout(mongodb_host, time.Duration(3)*time.Second)
	if err != nil {
		panic(err)
	}

	return session
}
