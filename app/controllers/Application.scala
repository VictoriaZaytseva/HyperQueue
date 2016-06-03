package controllers

import models.{TEvent, ConsumerResponse}
import services.SessionService
import services.TopicService
import java.util.UUID
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
import scala.collection.JavaConversions._

class Application extends Controller {


  val sessionService = new SessionService()
  val topicService = new TopicService()

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def consume(topicName: String) = Action {implicit request =>
    val sessionId = request.headers.get("Session") match {
      case Some(id) => {
        Logger.error("id is "+id)
        if(id == "")
          UUID.randomUUID()
        else
          UUID.fromString(id)}
      case None => UUID.randomUUID()
    }
    val position = sessionService.getCurrentPosition(sessionId, topicName)
    val result = topicService.consume(topicName, position)
    result match {
      case Some(event) =>  {
        val response = ConsumerResponse(event.name, event.description, sessionId)
        Ok(Json.toJson(response))
      }
      case None => NotFound("Topic "+topicName + "was not found. Ooooops")
    }
  }

  def produce(topicName: String) = Action(parse.json) {implicit request =>
    //verify topicName - BadRequest or move on
    // find topics, if topic doesnt exist add one
    request.body.validate[TEvent].fold(
      valid = postData => {
        topicService.push(topicName, postData)
        Ok("Success")
      },
      invalid = error => {
        BadRequest("Something is wrong with the request")
      }
    )
  }

  def checkPreFlight(topicName: String) = Action { implicit request =>
    // Return the pre-flight check with headers.
    Logger.debug("checking preflight")
    Ok("").withHeaders(
      ACCESS_CONTROL_ALLOW_ORIGIN -> "http://localhost:9001",
      ACCESS_CONTROL_ALLOW_METHODS -> "GET POST",
      ACCESS_CONTROL_ALLOW_HEADERS -> s"$ORIGIN, X-Requested-With, $CONTENT_TYPE, $ACCEPT, $AUTHORIZATION, X-Auth-Token")
  }
}
