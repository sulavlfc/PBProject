import scala.io.Source._

/**
  * Created by Sulav on 3/28/2016.
  */
object example {
  val lines = fromFile("stopwords_en.txt").getLines
  for ( l <- lines) println(l)
}
