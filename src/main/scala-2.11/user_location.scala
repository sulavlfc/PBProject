/**
  * Created by Sulav on 4/7/2016.
  */


/**
  * Created by Sulav on 3/30/2016.
  */

import com.mongodb.casbah.Imports._
import MongoFactory.location_collection
//{FileOutputStream, OutputStreamWriter, BufferedWriter}

import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext,Row}

import org.json4s.DefaultFormats
import org.json4s.native.Serialization._


import scala.collection.mutable
import scala.collection.immutable.SortedMap
import scala.collection.mutable.WrappedArray.ofRef


object user_location {

  def main(args: Array[String]) {
    implicit val formats = DefaultFormats
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)


    val tweettable = sqlContext.read.json("twitter_empire.json")
    //tweettable.printSchema()
    tweettable.registerTempTable("Movie")
    val json_data = sqlContext.sql("SELECT user.location FROM Movie where user.location IS NOT NULL").rdd

    val not_null_data = json_data.filter(j => j(0)!=null).countByValue().toSeq.sortBy(-_._2).take(10).map( n => location_collection.insert(MongoDBObject("location" -> n._1.get(0),"count" -> n._2 )))
   // println(not_null_data)
    /* for (n <- not_null_data){
 Map[Row,Long]
       movie_collection.insert(MongoDBObject("place" -> n._1.get(0),"count" -> n._2 ))

     }*/







  }
}



