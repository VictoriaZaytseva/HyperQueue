package models
import java.util.UUID
import play.api.libs.json.{ JsObject, Json }
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class ConsumerResponse(
                  name: String,
                  description: String,
                  sessionId: UUID
                ){}

/**
  * TODO: json post
  */

object ConsumerResponse{

  implicit val consumerResponseWrites = (
    (__ \ "name").write[String] and
    (__ \ "description").write[String] and
    (__ \ "sessionId").write[UUID]
    )(unlift(ConsumerResponse.unapply))
}