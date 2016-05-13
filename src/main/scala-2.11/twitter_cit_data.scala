/**
  * Created by Sulav on 4/6/2016.
  */
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



object Util_city {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("bIZgEbNKmgoOEDxheHXS0k6O5")
    .setOAuthConsumerSecret("sqahGfUd11OPMVbihn6LdcB0gccVVwi6CtJby6Ne27LwUTwg3a")
    .setOAuthAccessToken("327797166-Z5ul856pCScP0UNClnKyvOapLGte6oLBN0ayuh7b")
    .setOAuthAccessTokenSecret("AJ8nnTYV9W8hYgYbtqORj8yBruPswHzMNPRZtghfWfMSL")
    .setJSONStoreEnabled(true)
    .build
  //val f = new File("virus_data.json" )
  val fw = new FileWriter("city_data.json",true)
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

object StatusStreamer_city {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util_city.config).getInstance
    twitterStream.addListener(Util_city.simpleStatusListener)
    //val keyword = Array("batmanvssuperman","batmansuperman","batman v superman")

    //val missouri = Array(40.613628, -89.098747)//miami
    val NewYork = Array(Array(-74.255735,40.496044),Array(-73.700272,40.915256))
    val LosAngeles = Array(Array(-118.668176,33.703692),Array(-118.155289,34.337306))
    val Houston = Array(Array(-95.788087,29.523624),Array(-95.014496,30.110732))
    val Chicago = Array(Array(-87.940267,41.644335),Array(-87.524044,42.023131))
    val Kansas_city = Array(Array(-94.7659,38.827),Array(-94.3855,39.365))
    twitterStream.filter(new FilterQuery().locations(NewYork(0),NewYork(1)).locations(LosAngeles(0),LosAngeles(1)).locations(Houston(0),Houston(1)).locations(Chicago(0),Chicago(1)).locations(Kansas_city(0),Kansas_city(1)))
    Thread.sleep(21600000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}
