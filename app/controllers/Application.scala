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

/**
  * The main controller
  */
class Application extends Controller {


  val sessionService = new SessionService()
  val topicService = new TopicService()

  //hello world page
  //TODO: there might have been some description of current server status
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  /**
    * Method that handles the consumer's url
    * @param topicName
    * @return
    */
  def consume(topicName: String) = Action {implicit request =>
    val sessionId = request.headers.get("Session") match {
      case Some(id) => {
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

  /**
    * Method that is called when posting as producer
    * @param topicName
    * @return
    */
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

  /**
    * Custom override for the OPTIONS request, since I had to do crossdomain request with my localhost and the ports
    *
    * @param topicName
    * @return
    */
  def checkPreFlight(topicName: String) = Action { implicit request =>
    // Return the pre-flight check with headers.
    val clientUrl = Play.current.configuration.getString("client.url").getOrElse("")

    Ok("").withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> clientUrl,
      ACCESS_CONTROL_ALLOW_METHODS -> "GET POST",
      ACCESS_CONTROL_ALLOW_HEADERS -> "Content-Type, Session")
  }
}
