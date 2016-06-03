class TopicService {
  //map of all the topics
  var topics = Map[String, Topic]()

  def push(topicName: String, event: TEvent): Unit = {
    topics.get(topicName) match {
       //if topic already exists
      case Some(topic) =>{
        topic.insert(event)
      }
       //if not create a new topic
      case None => {
        var topic = new Topic()
        topic.insert(event)
        topics.+= ((topicName, topic))
      }
    }
  }

  def consume(topicName: String, position: Int): Option[TEvent] = {
    topics.get(topicName) match {
      case Some(topic) => Some(topic.get(position))
      case None => None
    }
  }
}