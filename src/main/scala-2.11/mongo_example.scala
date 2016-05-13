/**
  * Created by Sulav on 3/16/2016.
  */
import com.mongodb.casbah.Imports._

object mongo_example {
  def main(args: Array[String]) {
    val mongoClient = MongoClient("localhost", 27017)
    val db = mongoClient("test")
    db.collectionNames
    val coll = db("test")
    val a = MongoDBObject("hello" -> "world")
    val b = MongoDBObject("language" -> "scala")
    coll.insert( a )
    coll.insert( b )
    val allDocs = coll.find()
    println( allDocs )
    for(doc <- allDocs) println( doc )
  }
}
