/**
  * Created by Sulav on 4/1/2016.
  */

/**
  * Created by Sulav on 3/30/2016.
  */

import com.mongodb.casbah.Imports._
import MongoFactory.users_collection
//{FileOutputStream, OutputStreamWriter, BufferedWriter}

import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext,Row}

import org.json4s.DefaultFormats
import org.json4s.native.Serialization._

import org.apache.spark.sql.functions._
import scala.collection.mutable
import scala.collection.immutable.SortedMap
import scala.collection.mutable.WrappedArray.ofRef


object user_query {

  def main(args: Array[String]) {
    implicit val formats = DefaultFormats
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)


    val tweettable = sqlContext.read.json("user_data.json")
    tweettable.printSchema()
    tweettable.registerTempTable("User")
    val user_count = sqlContext.sql("SELECT user.name, user.followers_count FROM User").sort(desc("followers_count")).collect().take(10).map(u => users_collection.insert(MongoDBObject("name" -> u(0),"count" -> u(1)))) //show(20)
    //val red_data = user_count.takeOrdered(10)( Ordering[10])

    /*for ( u <- user_count){
      //println(u)





    }*/







  }
}



