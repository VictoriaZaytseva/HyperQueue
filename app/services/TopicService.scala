package services
import models.{Topic, TEvent}
import play.api.Logger

class TopicService {
  //map of all the topics
  var topics = Map[String, Topic]()

  def push(topicName: String, event: TEvent): Unit = {
    Logger.debug("pushing topic "+topicName+ " event " + event.toString)
    topics.get(topicName) match {
       //if topic already exists
      case Some(topic) =>{
        Logger.debug("topic already exists, old topic: ")
        Logger.debug(topic.toString)
        topic.insert(event)
      }
       //if not create a new topic
      case None => {
        Logger.debug("new topic was created@")
        Logger.debug("topics size" + topics.size.toString)
        var topic = new Topic()
        topic.insert(event)
        topics.+= ((topicName, topic))
        Logger.debug("new topics size")
        Logger.debug(topics.size.toString)
      }
    }
  }

  def consume(topicName: String, position: Int): Option[TEvent] = {
    topics.get(topicName) match {
      case Some(topic) => {
        if(position > (topic.size()-1))
          None
        else
          Some(topic.get(position))
      }
      case None => None
    }
  }
}