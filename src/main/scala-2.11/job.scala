/**
  * Created by Sulav on 3/13/2016.
  */

import java.io._

import MongoFactory.job_collection
import com.mongodb.casbah.Imports._


//{FileOutputStream, OutputStreamWriter, BufferedWriter}

import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext,Row}

import org.json4s.DefaultFormats
import org.json4s.native.Serialization._



import scala.collection.mutable
import scala.collection.mutable.WrappedArray.ofRef


object job {

  def main(args: Array[String]) {
    implicit val formats = DefaultFormats
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)


    val tweettable = sqlContext.read.json("job_data.json")
    //tweettable.printSchema()
    tweettable.registerTempTable("Job")
    val beach_data = sqlContext.sql("SELECT coordinates.coordinates, place.bounding_box.coordinates FROM Job")

    val bch_data = beach_data.map(z => location(z)).filter(z => z!= null).collect()


    for (b <- bch_data){

      if (b.isLeft){
        var coord = b.left.get

        job_collection.insert(MongoDBObject("long" -> coord(0),"lat" -> coord(1)))

      }
      else {
        var coord = b.right.get

        job_collection.insert(MongoDBObject("long" -> coord(0)(0)(0),"lat" -> coord(0)(0)(1)))
        //beach_collection.insert(MongoDBObject("lat" -> coord(0)(1)(0),"long" -> coord(0)(1)(1)))
        //beach_collection.insert(MongoDBObject("lat" -> coord(0)(2)(0),"long" -> coord(0)(2)(1)))
        //beach_collection.insert(MongoDBObject("lat" -> coord(0)(3)(0),"long" -> coord(0)(3)(1)))
      }

    }
  }


  //def location(row: Row): Either[[mutable.WrappedArray[Double], mutable.WrappedArray[mutable.WrappedArray[mutable.WrappedArray[Double]]]] = {
  def location(row: Row): Either[ mutable.WrappedArray[Double] , mutable.WrappedArray[mutable.WrappedArray[mutable.WrappedArray[Double]]]] = {
    if (row(0) != null) {
      val i = row(0) match {
        case x: mutable.WrappedArray[Double] => x // this extracts the value in a as an Int
        //case x => any: Any => x
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
