import java.util.UUID

class Session {

  private var positions = Map[UUID, Int]()

  def getPosition(session: UUID): Option[Int] ={
    positions.get(session)
  }

  def create(id: UUID): Unit ={
    positions.+=((id, 0))
  }

  def inc(session: UUID): Int = {
    positions(session)+=1
  }

}