package models

import play.api.Logger
import java.util.UUID
import scala.collection.mutable.Map

/**
  * This structure holds the maps with the positions of current consumers
  */
class Session {

  private var positions = Map[UUID, Map[String, Int]]()

  def getPosition(session: UUID, topicName: String): Option[Int] ={
    positions.get(session) match {
      case Some(data) =>  data.get(topicName)
      case None => None
    }
  }

  def sessionExists(session: UUID): Boolean ={
    positions.contains(session)
  }

  def create(id: UUID): Unit ={
    positions.+=((id, Map[String, Int]()))
  }

  def createTopic(sessionId: UUID, topicName: String): Unit = {
    positions(sessionId)(topicName) = 0
  }

  def inc(session: UUID, topicName: String): Unit = {
    positions(session)(topicName)= positions(session)(topicName)+1
  }
}