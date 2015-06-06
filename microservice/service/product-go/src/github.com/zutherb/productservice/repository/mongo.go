package repository
import (
	"gopkg.in/mgo.v2"
	"os"
)

func NewSession() *mgo.Session {
	mongodb_host := os.Getenv("MONGODB_PORT_27017_TCP_ADDR")

	if mongodb_host == "" {
		mongodb_host = "mongodb-node"
	}

	session, err := mgo.Dial(mongodb_host)
	if err != nil {
		panic(err)
	}
	defer session.Close()

	return session
}