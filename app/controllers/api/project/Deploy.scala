package controllers.api.project

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import models._
import play.api._
import play.api.libs.json.{Json,JsError}
import play.api.mvc._
import scala.util.Try
import util.JsonFormats._

object Deploy extends Controller {

  val dateParser = ISODateTimeFormat.dateTimeParser()
  val dateFormatter = ISODateTimeFormat.dateTime()

  def create(projectId: Long) = Action(BodyParsers.parse.json) { request =>
    request.body.validate[Deploy] fold(
      valid = { deploy =>
        DeployModel.create(projectId, deploy) map { result =>
          Created(Json.toJson(result))
        } getOrElse {
          InternalServerError
        }
      },
      invalid = { errors =>
        BadRequest(Json.obj(
          "message" -> JsError.toFlatJson(errors)
        ))
      }
    )
  }

  def delete(projectId: Long, id: Long) = Action {
    DeployModel.deleteById(projectId, id)
    NoContent
  }

  def search(projectId: Long, device: Option[String], date: Option[String]) = Action {
    // Not happy with this logic. Same in artifact
    if(device.isDefined) {
      // If we don't get a date, use the current date.
      val deployDate = date getOrElse {
        dateFormatter.print(new DateTime())
      }

      Try {
        dateParser.parseDateTime(deployDate)
      } map { dt =>
        Ok(Json.toJson(DeployModel.getByDate(projectId, device.get, dt)))
      } getOrElse {
        BadRequest(Json.obj(
          "message" -> "Bad date format, please use ISO 8601"
        ))
      }
    } else {
      if(date.isDefined) {
        BadRequest(Json.obj(
          "message" -> "Dates can only be used with a specific device parameter"
        ))
      } else {
        Ok(Json.toJson(DeployModel.getAllForProject(projectId)))
      }
    }
  }

  def item(projectId: Long, id: Long) = Action {
    DeployModel.getById(projectId, id) map { deploy =>
      Ok(Json.toJson(deploy))
    } getOrElse(NotFound)
  }
}
