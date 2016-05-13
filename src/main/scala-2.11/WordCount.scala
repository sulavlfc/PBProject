/**
  * Created by Sulav on 2/20/2016.
  */
import java.io._
import scalaz._,Scalaz._
import std.option._, std.list._
import scalaz.syntax._


import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.sql.SQLContext

import scala.io.Source
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.DefaultFormats

import org.json4s.jackson.JsonMethods
import org.json4s.jackson.JsonMethods._
import org.json4s.native.Serialization.{write,writePretty}

case class place(
                  url: String,
                  country: String,
                  place_type: String,
                  //bounding_box : Map[String,Any]
                  bounding_box : Bounding_box
                )

case class Bounding_box(
                       `type`: String,
                        //coordinates : Array[Array[Array[Double]]]
                       coordinates : List[List[List[Double]]]
                       )

case class retweeted_status(
                  text: String
                )
case class quoted_status(
                        text: String
                        )
case class coordinates(
                      coordinates : List[Double]
                      )

case class final_data(
                        latitude : Double,
                        longitude : Double
                      )

object WordCount {

  def main(args: Array[String]) {
    implicit val formats = DefaultFormats
    val AppName = "WordCountJob"
    val conf = new SparkConf().setAppName(AppName).setMaster("local")
    val sc = new SparkContext(conf)
    val textFile = sc.textFile("virus_data1.json")
    //val textFile = sc.textFile("user1.txt")
    //spark map to extract required json
    val json_val = textFile.map(line => parse_json(line))//.collect()
    //val zika_filter = json_val.filter(jval => jval._1.contains("zika") || jval._2.contains("zika"))
    //println(zika_filter)
    val all_tweets = json_val.map(jval => ((jval._1 + jval._2 + jval._3 ),jval._4,jval._5))
    val zika_filter = all_tweets.filter(zval => zval._1.contains("zika"))
    //for (z <- zika_filter) println(z)
    //val zika_location = zika_filter.flatMap(zval => extract_val(zval))
    val zika_location = zika_filter.map(zval => extract_val(zval))
    val zika_final_loation = zika_location.filter(zval => zval!= null ).collect()
    //val zika_final_location = zika_final_loation.filter(zval => zval!= null).collect()

    //val fw = new FileWriter("zika.json", true)
    val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("zikalocation.json")))

    for (z <- zika_final_loation){
      //if (z.length == 0) println(z)
      //println(z.joinLeft)
      if (z.length == 1) {
        val reqd_val = z.right.get
        println(reqd_val.length)
        writer.write(reqd_val(0) +","+reqd_val(1)+'\n')
      }
      else if(z.length==0){
        val reqd_val = z.left.get
        println(reqd_val.length)

        for (x <- reqd_val) {
          // however you want to format it
          for (y <- x) {
            println(y(0))
            println(y(1))
            val write_val = final_data(y(0),y(1))
            val json_zikaval = write(write_val)
            println(writePretty(json_zikaval))
            //writer.write(y(0) +","+y(1)+'\n')
            writer.append(writePretty(json_zikaval)+'\n')
          }
          //println(x.length)
        }
      }

    }
    writer.close()

  }

    def parse_json(line: JsonInput): (String, String, String, List[Double],List[List[List[Double]]]) = {

      implicit val formats = DefaultFormats
      val json = parse(line)
      //outer_text
      var text = json \ "text"

      val outer_text = text.extract[String].toLowerCase()
      //retweeted_text
      var rt_status: String = ""
      val rt_text = json \ "retweeted_status"
      if (rt_text == JNothing) {
        rt_status = null
      }

      else {
        val retweeted_status = rt_text.extract[retweeted_status]
        if (retweeted_status == null) {
          rt_status = null
        }
        else {
          rt_status = retweeted_status.text.toLowerCase()
        }
      }
      //quoted_status
      var qt_status: String = ""
      val qt_text = json \ "quoted_status"
      if (qt_text == JNothing) {
        qt_status = null
      }

      else {
        val quoted_status = qt_text.extract[quoted_status]
        if (quoted_status == null) {
          qt_status = null
        }
        else {
          qt_status = quoted_status.text.toLowerCase()
          //println(rt_status)
        }
      }
      //bounding_box
      var coordinates_list: List[List[List[Double]]] = List()
      val evval = json \ "place"
      val element = evval.extract[place]
      if (element == null) {
         coordinates_list = null

      }
      else {
        var bbox = evval \\ "bounding_box"
        val bbox_elem = bbox.extract[Bounding_box]
        coordinates_list = bbox_elem.coordinates


      }
      //Point Coordinate
      var coord: List[Double] = List()
      val coordinate = json \ "coordinates"
      //println(coordinates)
      val cord_val = coordinate.extract[coordinates]
      if (cord_val == null) coord = null
      else coord = cord_val.coordinates
      return (outer_text, rt_status,qt_status,coord,coordinates_list)
    }

   /* def extract_val(tuple: Tuple3[String,List[Double],List[List[List[Double]]]]): (String) ={
      //println(tuple3)
      return tuple._1

    }*/

   def extract_val(tuple: Tuple3[String,List[Double],List[List[List[Double]]]]): Either[List[List[List[Double]]],List[Double]]={
     //println(tuple3)
     //var final_location = List()
     //var point_location: List[Double] = List()
     //var bounding_location: List[List[List[Double]]] = List()

     if (tuple._2 == null) {
       //bounding_location = tuple._3
       //return bounding_location
       if (tuple._3 == null) return null//return null
       else  Left(tuple._3) //return tuple._3
     }
     else   Right(tuple._2)//return tuple._2 //point_location = tuple._2
    }
  }

