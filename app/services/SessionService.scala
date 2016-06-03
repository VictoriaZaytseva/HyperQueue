package services

import java.util.UUID
import models.Session
import play.api.Logger
class SessionService {
  var session = new Session();
  /**
    * Get current position
    * TODO: implement the case where position is bigger than the queue
     */
  def getCurrentPosition(sessionId: UUID, topicName: String): Int = {
     if(session.sessionExists(sessionId)) {
       session.getPosition(sessionId, topicName) match {
         case Some(position) => {
           session.inc(sessionId, topicName)
           position
         }
         case None => {
           session.createTopic(sessionId, topicName)
           session.inc(sessionId, topicName)
           0
         }
       }
     } else {
         Logger.debug("Creating new session")
         session.create(sessionId)
         session.createTopic(sessionId, topicName)
         session.inc(sessionId, topicName)
         0
       }
    }
}