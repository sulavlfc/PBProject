/**
  * Created by Sulav on 4/7/2016.
  */

/**
  * Created by Sulav on 3/30/2016.
  */

import java.io._



//{FileOutputStream, OutputStreamWriter, BufferedWriter}

import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext,Row}

import org.json4s.DefaultFormats

import MongoFactory.topic_collection
import com.mongodb.casbah.commons.MongoDBObject

import org.joda.time.DateTime
import java.text.SimpleDateFormat;
import java.util.{Date,Calendar};
import scala.reflect.macros.ParseException
import scala.io.Source._

object topic_detection {

  def main(args: Array[String]) {
    implicit val formats = DefaultFormats
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)


    val tweettable = sqlContext.read.json("user_data.json")
    //tweettable.printSchema()
    tweettable.registerTempTable("TOPIC")

    val json_data = sqlContext.sql("SELECT lower(text) FROM TOPIC WHERE text IS NOT NULL")

    //val counts = json_data.map(word => (word, 1)).reduceByKey(_ + _).sortBy(_._2)
    val lines = fromFile("stopwords_en.txt").getLines.toArray
    //json_data
    val counts = json_data.flatMap(line => line(0).toString.split(" ")).filter(word => !lines.contains(word)).map(word => (word, 1)).reduceByKey(_ + _).sortBy(_._2,false).take(20).map(word => topic_collection.insert(MongoDBObject("name" -> word._1, "count" -> word._2)))

    //println(counts)
     // .map(word => topic_collection.insert(MongoDBObject("name" -> word._1, "count" -> word._2)))

    /*for (word <- counts) {
      println(word)
      topic_collection.insert(MongoDBObject("name" -> word._1, "count" -> word._2))

    }*/

  }




}

