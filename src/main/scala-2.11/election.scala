/**
  * Created by Sulav on 3/16/2016.
  */


/**
  * Created by Sulav on 3/13/2016.
  */

import java.io._ //{FileOutputStream, OutputStreamWriter, BufferedWriter}

import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext,Row}
import MongoFactory.election_collection
import com.mongodb.casbah.Imports._
import org.json4s.DefaultFormats


object election {

  def main(args: Array[String]) {
    implicit val formats = DefaultFormats
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)


    val tweettable = sqlContext.read.json("election.json")
    //tweettable.printSchema()
    tweettable.registerTempTable("Election")
    val json_data = sqlContext.sql("SELECT text FROM Election").rdd
    val not_null_data = json_data.filter(j => j(0)!=null)
    val trump_data = not_null_data.filter(j => j(0).toString.toLowerCase.contains("trump")).collect()
    val clinton_data = not_null_data.filter(j => j(0).toString.toLowerCase.contains("clinton")).collect()
    val sanders_data = not_null_data.filter(j => j(0).toString.toLowerCase.contains("sanders")).collect()
    //val rubio_data = not_null_data.filter(j => j(0).toString.toLowerCase.contains("rubio")).collect()
    val cruz_data = not_null_data.filter(j => j(0).toString.toLowerCase.contains("cruz")).collect()
    //json_data.filter(z => z(0).)
    election_collection.insert(MongoDBObject("trump" -> trump_data.length, "clinton" -> clinton_data.length, "sanders" -> sanders_data.length,"cruz" -> cruz_data.length))
  }

}
