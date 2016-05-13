/**
  * Created by Sulav on 3/30/2016.
  */

import java.io._ //{FileOutputStream, OutputStreamWriter, BufferedWriter}

import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext,Row}
import MongoFactory.timechart_collection
import com.mongodb.casbah.Imports._
import org.json4s.DefaultFormats

import org.joda.time.DateTime

import java.text.SimpleDateFormat;
import java.util.{Date,Calendar};
import scala.reflect.macros.ParseException


object timechart {

  def main(args: Array[String]) {
    implicit val formats = DefaultFormats
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)


    val tweettable = sqlContext.read.json("election.json")
    //tweettable.printSchema()
    tweettable.registerTempTable("TIMECHART")
    val json_data = sqlContext.sql("SELECT created_at FROM TIMECHART").rdd
    val timedata = json_data.filter(j => j(0)!= null).map(j => getTwitterDate(j(0).toString)).countByValue().map(t => timechart_collection.insert(MongoDBObject("x" -> t._1, "y" -> t._2)))
    //val data = timedata.countByValue()
    //for ( j <- json_data) println(j(0))
    //for ( j <- timedata) println(j)
    /*for (t <- timedata) {


      timechart_collection.insert(MongoDBObject("x" -> t._1, "y" -> t._2))
    }*/




  }

  def getTwitterDate(string: String) : Any  = {

    val twitter ="EEE MMM dd HH:mm:ss ZZZZZ yyyy"
    val sf = new SimpleDateFormat(twitter)
    //sf.setLenient(true)
    val stringtime = sf.parse(string)
    val mycal = Calendar.getInstance()
    val mydate = mycal.setTime(stringtime)
    val month = mycal.get(Calendar.MONTH)

    val date1 = new DateTime(mycal.get(Calendar.YEAR), month+1, mycal.get(Calendar.DATE), mycal.get(Calendar.HOUR_OF_DAY),mycal.get(Calendar.MINUTE),0,0)
    val date = Array(mycal.get(Calendar.YEAR),month+1,mycal.get(Calendar.DATE), mycal.get(Calendar.HOUR_OF_DAY),0,0,0)

    return (mycal.get(Calendar.YEAR),month,mycal.get(Calendar.DATE), mycal.get(Calendar.HOUR_OF_DAY),0,0,0)
    //return (mycal.get(Calendar.YEAR),month,mycal.get(Calendar.DATE), mycal.get(Calendar.HOUR_OF_DAY),mycal.get(Calendar.MINUTE),0,0)
    //return date1
  }



}
