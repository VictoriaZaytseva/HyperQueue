package services

import java.util.UUID
import models.Session

class SessionService {
  var session = new Session();
  /**
    * Get current position
     */
  def getCurrentPosition(sessionId: UUID): Int = {
     session.getPosition (sessionId) match {
       case Some(position) => position
       case None => {
         session.create(sessionId)
         0
       }
     }
  }
}