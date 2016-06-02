import scala.collection.mutable.ListBuffer

/**
  * Broker´s queue of the events
  * @param name name of the queue
  */
class Topic(name: String) {
  // Using Scala list buffer because it will provide a fast removal of first and the last element
  // Although, we don´t need the removal of the last element, but we can use the faster removal of the first one
  // when we want to remove timed out elements of the queue
  private var eventQueue = ListBuffer[TEvent]()

  def get(index: Int): TEvent = {
    eventQueue(index)
  }

  def insert(event: TEvent): Unit ={
    eventQueue.+=(event)
  }

  //removing the first element like in a good old queue
  def pop(): TEvent = {
    eventQueue.remove(0)
  }

}