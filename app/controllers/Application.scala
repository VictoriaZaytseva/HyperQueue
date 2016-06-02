package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def consume(topicName: String) = Action.async{

  }

  def produce(topicName: String) = Action.async{
    //verify topicName - BadRequest or move on
    // find topics, if topic doesnt exist add one
    //
  }

}
