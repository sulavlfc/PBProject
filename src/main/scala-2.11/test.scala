import scala.io.Source._

/**
  * Created by Sulav on 2/28/2016.
  */
object test {

  def main(args: Array[String]) {

    var c: Array[String] = Array()
    val lines = fromFile("stopwords_en.txt").getLines.toArray
    if (lines.contains("a")){
      println("yes")
      println(lines.length)
    }

  }
}

