package models
/**
  * An event entity
  * @param name
  * @param description
  */
import play.api.libs.json.{ JsObject, Json }
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class TEvent(
                 name: String,
                 description: String
                 ){}

/**
  * TODO: json post
  */

object TEvent{
  implicit val postReads = (
    (__ \ "name").read[String] and
    (__ \ "description").read[String]
    )(TEvent.apply _)

  implicit val tEventWrites = (
    (__ \ "name").write[String] and
    (__ \ "description").write[String]
    )(unlift(TEvent.unapply))
}
