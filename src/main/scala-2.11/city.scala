/**
  * Created by Sulav on 4/6/2016.
  */
/**
  * Created by Sulav on 3/30/2016.
  */

import com.mongodb.casbah.Imports._
import MongoFactory.cities_collection
//{FileOutputStream, OutputStreamWriter, BufferedWriter}

import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext,Row}

import org.json4s.DefaultFormats
import org.json4s.native.Serialization._


import scala.collection.mutable
import scala.collection.immutable.SortedMap
import scala.collection.mutable.WrappedArray.ofRef


object city {

  def main(args: Array[String]) {
    implicit val formats = DefaultFormats
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val req_array = Array("Kansas City","Chicago","New York")
    val tweettable = sqlContext.read.json("cities_data.json")
    tweettable.printSchema()
    tweettable.registerTempTable("City")
    val json_data = sqlContext.sql("SELECT place.name FROM City").rdd
    //for (j <-json_data) println(j(0))

    val not_null_data = json_data.filter(j => j(0)!=null).countByValue().toSeq.sortBy(-_._2).map( n => cities_collection.insert(MongoDBObject("place" -> n._1.get(0),"count" -> n._2 )))








  }
}



