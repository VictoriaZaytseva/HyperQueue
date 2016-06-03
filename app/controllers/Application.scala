package controllers

import play.api._
import play.api.mvc._
import services.SessionService
import services.TopicService
import play.api.Logger
import java.util.UUID
import play.api.libs.json.{ JsObject, Json }
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json._

class Application extends Controller {

  val sessionService = new SessionService()
  val topicService = new TopicService()

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def consume(topicName: String) = Action {implicit request =>
    val sessionId = request.headers.get("Session") match {
      case Some(id) => UUID.fromString(id)
      case None => UUID.randomUUID()
    }
    val position = sessionService.getCurrentPosition(sessionId)
    val result = topicService.consume(topicName, position)
    result match {
      case Some(response) =>  Ok(Json.toJson(result))
      case None => NotFound("Topic "+topicName + "was not found. Ooooops")
    }
  }

  def produce(topicName: String) = Action(parse.json) {implicit request =>
    //verify topicName - BadRequest or move on
    // find topics, if topic doesnt exist add one
    //
    Ok("hello p")
  }

}
