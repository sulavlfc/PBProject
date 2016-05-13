/**
  * Created by Sulav on 3/16/2016.
  */
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.MongoConnection
object MongoFactory {
  private val SERVER = "localhost"
  private val PORT = 27017
  private val DATABASE = "ProjectDataBase"
  private val ELECTION_COLLECTION = "election"
  private val JOB_COLLECTION = "job"
  private val VIRUS_COLLECTION = "virus"
  private val MOVIE_COLLECTION = "movie"
  private val EGYPTAIR_COLLECTION = "egyptair"
  private val USERS_COLLECTION = "users"
  private val CITES_COLLECTION = "cities"
  private val TIMECHART_COLLECTION = "timechart"
  private val LOCATION_COLLECTION = "location"
  private val TOPIC_COLLECTION = "topic"

  val connection = MongoConnection(SERVER)
  val election_collection = connection(DATABASE)(ELECTION_COLLECTION)
  val job_collection = connection(DATABASE)(JOB_COLLECTION)
  val virus_collection = connection(DATABASE)(VIRUS_COLLECTION)
  val movie_collection = connection(DATABASE)(MOVIE_COLLECTION)
  val egyptair_collection = connection(DATABASE)(EGYPTAIR_COLLECTION)
  val users_collection = connection(DATABASE)(USERS_COLLECTION)
  val cities_collection = connection(DATABASE)(CITES_COLLECTION)
  val timechart_collection = connection(DATABASE)(TIMECHART_COLLECTION)
  val topic_collection = connection(DATABASE)(TOPIC_COLLECTION)
  val location_collection = connection(DATABASE)(LOCATION_COLLECTION)
}