package models

import java.util.UUID
import scala.collection.mutable.Map

class Session {

  private var positions = Map[UUID, Int]()

  def getPosition(session: UUID): Option[Int] ={
    positions.get(session)
  }

  def create(id: UUID): Unit ={
    positions.+=((id, 0))
  }

  def inc(session: UUID): Unit = {
    positions(session)= positions(session)+1
  }
}