/**
  * Created by Sulav on 4/6/2016.
  */

/**
  * Created by Sulav on 2/28/2016.
  */
import twitter4j._
import twitter4j.TwitterObjectFactory
import java.io._
import java.io.FileWriter;
import java.io.BufferedWriter;



object Util_panama {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("tGgiMdJ7ua3xgHu0I0Gdxbh90")
    .setOAuthConsumerSecret("fr9vneybOfO6b90Eevg5Hs8NWiQ0HOnF2eB5huVl5a4feVWmra")
    .setOAuthAccessToken("400618597-Ui7TksvLKXN4HCI6FMs2D1OzkPjsZX5n8a80Wn10")
    .setOAuthAccessTokenSecret("KsCmcjgR4oRdUYR08ud5w6JVgyAcFwKwyD1UroOp5pXF6")
    .setJSONStoreEnabled(true)
    .build
  //val f = new File("virus_data.json" )
  val fw = new FileWriter("panama_paper.json",true)
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

object StatusStreamer_panama {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util_panama.config).getInstance
    twitterStream.addListener(Util_panama.simpleStatusListener)
    //val keyword = Array("elclasico","elclassico","el clasico","el classico")
    val keyword = Array("panama paper","panamapaper")
    //val missouri = Array(40.613628, -89.098747)//miami
    //val austinBox = Array(-97.8,30.25,-97.65,30.35)
    twitterStream.filter(new FilterQuery().track(keyword(0),keyword(1)))//.locations(austinBox))

    //twitterStream.filter(new FilterQuery().locations(missouri,Array(-97.8,30.25)).track(keyword(0)))//.locations(pacificbeach).locations(waikikibeach))//.locations())  //.track(keyword(0),keyword(1)).locations(miamibeach))//.locations(miamibeach,pacificbeach,waikikibeach))
    //twitterStream.filter(new FilterQuery().track(keyword(0),keyword(1),keyword(2),keyword(3)))//.locations(austinBox))
    //twitterStream.sample()
    Thread.sleep(21600000)

  }
}

