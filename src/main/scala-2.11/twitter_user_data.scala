/**
  * Created by Sulav on 4/1/2016.
  */

/**
  * Created by Sulav on 2/28/2016.
  */
import twitter4j._
import twitter4j.TwitterObjectFactory
import java.io._
import java.io.FileWriter;
import java.io.BufferedWriter;



object Util_user {
  //authnetication
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("tGgiMdJ7ua3xgHu0I0Gdxbh90")
    .setOAuthConsumerSecret("fr9vneybOfO6b90Eevg5Hs8NWiQ0HOnF2eB5huVl5a4feVWmra")
    .setOAuthAccessToken("400618597-Ui7TksvLKXN4HCI6FMs2D1OzkPjsZX5n8a80Wn10")
    .setOAuthAccessTokenSecret("KsCmcjgR4oRdUYR08ud5w6JVgyAcFwKwyD1UroOp5pXF6")
    .setJSONStoreEnabled(true)
    .build

  //declaring the file name
  val fw = new FileWriter("user_data.json",true)
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

object StatusStreamer_user {
  def main(args: Array[String]) {
    //authentication from main
    val twitterStream = new TwitterStreamFactory(Util_user.config).getInstance

    //handle the tweets
    twitterStream.addListener(Util_user.simpleStatusListener)
    //val keyword = Array("zika","ebola","yellow fever")
    //twitterStream.filter(new FilterQuery().track(keyword(0),keyword(1),keyword(2)))
    twitterStream.sample()
    Thread.sleep(10800000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}

