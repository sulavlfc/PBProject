/**
  * Created by Sulav on 2/28/2016.
  */
import twitter4j._
import twitter4j.TwitterObjectFactory
import java.io._
import java.io.FileWriter;
import java.io.BufferedWriter;



object Util_virus {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("bIZgEbNKmgoOEDxheHXS0k6O5")
    .setOAuthConsumerSecret("sqahGfUd11OPMVbihn6LdcB0gccVVwi6CtJby6Ne27LwUTwg3a")
    .setOAuthAccessToken("327797166-Z5ul856pCScP0UNClnKyvOapLGte6oLBN0ayuh7b")
    .setOAuthAccessTokenSecret("AJ8nnTYV9W8hYgYbtqORj8yBruPswHzMNPRZtghfWfMSL")
    .setJSONStoreEnabled(true)
    .build
  //val f = new File("virus_data.json" )
  val fw = new FileWriter("virus_data.json",true)
  //File f = new File("pathToYourFile");



  def simpleStatusListener = new StatusListener() {


    def onStatus(status: Status) {

      //val pw = new PrintWriter(fw)
      val data = TwitterObjectFactory.getRawJSON(status)
      println(data)
      //pw.append(data + '\n')

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

object StatusStreamer_virus {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util_virus.config).getInstance
    twitterStream.addListener(Util_virus.simpleStatusListener)
    val keyword = Array("zika","ebola","yellow fever")
    twitterStream.filter(new FilterQuery().track(keyword(0),keyword(1),keyword(2)))
    twitterStream.sample()
    Thread.sleep(21600000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}
