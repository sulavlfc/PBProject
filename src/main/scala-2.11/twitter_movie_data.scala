/**
  * Created by Sulav on 3/30/2016.


*
  * Created by Sulav on 2/28/2016.
  */
import twitter4j._
import twitter4j.TwitterObjectFactory
import java.io._
import java.io.FileWriter;
import java.io.BufferedWriter;



object Util_movie {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("tGgiMdJ7ua3xgHu0I0Gdxbh90")
    .setOAuthConsumerSecret("fr9vneybOfO6b90Eevg5Hs8NWiQ0HOnF2eB5huVl5a4feVWmra")
    .setOAuthAccessToken("400618597-Ui7TksvLKXN4HCI6FMs2D1OzkPjsZX5n8a80Wn10")
    .setOAuthAccessTokenSecret("KsCmcjgR4oRdUYR08ud5w6JVgyAcFwKwyD1UroOp5pXF6")
    .setJSONStoreEnabled(true)
    .build
  //val f = new File("virus_data.json" )
  val fw = new FileWriter("movie_data.json",true)
  //File f = new File("pathToYourFile");



  def simpleStatusListener = new StatusListener() {


    def onStatus(status: Status) {

      //val pw = new PrintWriter(fw)
      val data = TwitterObjectFactory.getRawJSON(status)

      //pw.append(data + '\n')
      println(data)

      fw.append(data + '\n')


    }

    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }
  //fw.close()
}

object StatusStreamer_movie {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util_movie.config).getInstance
    twitterStream.addListener(Util_movie.simpleStatusListener)
    val keyword = Array("batmanvssuperman","batmansuperman","batman v superman")

    //val missouri = Array(40.613628, -89.098747)//miami
    //val NewYork = Array(Array(-74.255735,40.496044),Array(-73.700272,40.915256))
    //val LosAngeles = Array(Array(-118.668176,33.703692),Array(-118.155289,34.337306))
    //val Houston = Array(Array(-95.788087,29.523624),Array(-95.014496,30.110732))
    //val Chicago = Array(Array(-87.940267,41.644335),Array(-87.524044,42.023131))
    twitterStream.filter(new FilterQuery().track(keyword(0),keyword(1),keyword(2)))//.locations(NewYork(0),NewYork(1)).locations(LosAngeles(0),LosAngeles(1)).locations(Houston(0),Houston(1)).locations(Chicago(0),Chicago(1)))
    Thread.sleep(21600000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}
