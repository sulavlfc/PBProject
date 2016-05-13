/**
  * Created by Sulav on 3/1/2016.
  */

import java.io._

import MongoFactory.virus_collection
import com.mongodb.casbah.Imports._

//{FileOutputStream, OutputStreamWriter, BufferedWriter}

import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext,Row}

import org.json4s.DefaultFormats
import org.json4s.native.Serialization._


import scala.collection.mutable
import scala.collection.mutable.WrappedArray.ofRef


object virus {

  def main(args: Array[String]) {
    implicit val formats = DefaultFormats
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)


    val tweettable = sqlContext.read.json("virus_data.json")
    tweettable.printSchema()
    tweettable.registerTempTable("Tweet")
    //val json_data = sqlContext.sql("SELECT text, quoted_status.text, retweeted_status.text, coordinates.coordinates, place.bounding_box.coordinates FROM Tweet").collect()

    val zika_data = sqlContext.sql("SELECT coordinates.coordinates, place.bounding_box.coordinates FROM Tweet WHERE LOWER(text) LIKE '%zika%' OR LOWER(retweeted_status.text) LIKE '%zika%' OR LOWER(quoted_status.text) LIKE '%zika%'")
    //val zika_data = sqlContext.sql("SELECT text FROM Tweet WHERE LOWER(text) LIKE '%the%' OR LOWER(retweeted_status.text) LIKE '%the%' OR LOWER(quoted_status.text) LIKE '%the%'")
    //for (z <- zika_data) println(z)
    val zk = zika_data.map(z => location(z)).filter(z => z!= null).collect()
    //val zk = zika_data.filter(z => z!= null).map(z => location(z)).collect()

    for (z<- zk){
      println(z)
      if (z.isLeft){
        val coord = z.left.get
        println(coord)
        virus_collection.insert(MongoDBObject("type"-> "zika","long" -> coord(0),"lat" -> coord(1)))
      }
      else {
        val coord = z.right.get
        println(coord)
        virus_collection.insert(MongoDBObject("type"-> "zika","long" -> coord(0)(0)(0),"lat" -> coord(0)(0)(1)))
      }
    }


    val ebola_data = sqlContext.sql("SELECT coordinates.coordinates, place.bounding_box.coordinates FROM Tweet WHERE LOWER(text) LIKE '%ebola%' OR LOWER(retweeted_status.text) LIKE '%ebola%' OR LOWER(quoted_status.text) LIKE '%ebola%'")
    //val ebola_data = sqlContext.sql("SELECT text FROM Tweet WHERE LOWER(text) LIKE '%the%' OR LOWER(retweeted_status.text) LIKE '%the%' OR LOWER(quoted_status.text) LIKE '%the%'")
    //for (z <- ebola_data) println(z)
    val eb = ebola_data.map(z => location(z)).filter(z => z!= null).collect()
    //val zk = ebola_data.filter(z => z!= null).map(z => location(z)).collect()

    for (e<- eb){
      println(e)
      if (e.isLeft){
        val coord = e.left.get
        println(coord)
        virus_collection.insert(MongoDBObject("type"-> "ebola","long" -> coord(0),"lat" -> coord(1)))
      }
      else {
        val coord = e.right.get
        println(coord)
        virus_collection.insert(MongoDBObject("type"-> "ebola","long" -> coord(0)(0)(0),"lat" -> coord(0)(0)(1)))
      }
    }


    val flu_data = sqlContext.sql("SELECT coordinates.coordinates, place.bounding_box.coordinates FROM Tweet WHERE LOWER(text) LIKE '%flu%' OR LOWER(retweeted_status.text) LIKE '%flu%' OR LOWER(quoted_status.text) LIKE '%flu%'")
    //val yellow_fever_data = sqlContext.sql("SELECT text FROM Tweet WHERE LOWER(text) LIKE '%the%' OR LOWER(retweeted_status.text) LIKE '%the%' OR LOWER(quoted_status.text) LIKE '%the%'")
    //for (z <- yellow_fever_data) println(z)
    val yf = flu_data.map(z => location(z)).filter(z => z!= null).collect()
    //val zk = yellow_fever_data.filter(z => z!= null).map(z => location(z)).collect()

    for (y<- yf){
      println(y)
      if (y.isLeft){
        val coord = y.left.get
        println(coord)
        virus_collection.insert(MongoDBObject("type"-> "flu","long" -> coord(0),"lat" -> coord(1)))
      }
      else {
        val coord = y.right.get
        println(coord)
        virus_collection.insert(MongoDBObject("type"-> "flu","long" -> coord(0)(0)(0),"lat" -> coord(0)(0)(1)))
      }
    }

    val hanta_data = sqlContext.sql("SELECT coordinates.coordinates, place.bounding_box.coordinates FROM Tweet WHERE LOWER(text) LIKE '%hanta%' OR LOWER(retweeted_status.text) LIKE '%hanta%' OR LOWER(quoted_status.text) LIKE '%hanta%'")

    val hf = hanta_data.map(z => location(z)).filter(z => z!= null).collect()


    for (h<- hf){

      if (h.isLeft){
        val coord = h.left.get
        println(coord)

        virus_collection.insert(MongoDBObject("type"-> "hanta","long" -> coord(0),"lat" -> coord(1)))
      }
      else {
        val coord = h.right.get
        println(coord)
        virus_collection.insert(MongoDBObject("type"-> "hanta","long" -> coord(0)(0)(0),"lat" -> coord(0)(0)(1)))
      }
    }

  }


  def location(row: Row): Either[mutable.WrappedArray[Double], mutable.WrappedArray[mutable.WrappedArray[mutable.WrappedArray[Double]]]] = {

    if (row(0) != null) {
      val i = row(0) match {
        case x: mutable.WrappedArray[Double] => x // this extracts the value in a as an Int
        //case _ => Int.MinValue
      }

      return Left(i)
    }
    else {
      if (row(1) == null) return null
      else {
        val i = row(1) match {
          case x: mutable.WrappedArray[mutable.WrappedArray[mutable.WrappedArray[Double]]] => x // this extracts the value in a as an Int
          //case _ => Int.MinValue
        }
        return Right(i)
      }


    }
  }

}
