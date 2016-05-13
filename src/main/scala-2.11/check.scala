import scala.io.Source._

object check {
  val lines = fromFile("stopwords_en.txt").getLines
  for ( l <- lines) println(l)
}
