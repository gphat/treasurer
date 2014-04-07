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

  def index(projectId: Long) = Action {
    Ok(Json.toJson(DeployModel.getAllForProject(projectId)))
  }

  def search(projectId: Long, device: String, date: Option[String]) = Action {
    // If we don't get a date, use the current date.
    val deployDate = date getOrElse {
      dateFormatter.print(new DateTime())
    }

    Try {
      dateParser.parseDateTime(deployDate)
    } map { dt =>
      Ok(Json.toJson(DeployModel.getByDate(projectId, device, dt)))
    } getOrElse {
      BadRequest(Json.obj(
        "message" -> "Bad date format, please use ISO 8601"
      ))
    }
  }

  def item(projectId: Long, id: Long) = Action {
    DeployModel.getById(projectId, id) map { deploy =>
      Ok(Json.toJson(deploy))
    } getOrElse(NotFound)
  }
}
