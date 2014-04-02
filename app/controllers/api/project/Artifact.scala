package controllers.api.project

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import models._
import play.api._
import play.api.libs.json.{Json,JsError}
import play.api.mvc._
import scala.util.Try
import util.JsonFormats._

object Artifact extends Controller {

  val dateFormat = ISODateTimeFormat.dateTimeParser()

  def create(projectId: Long) = Action(BodyParsers.parse.json) { request =>
    request.body.validate[Artifact] fold(
      valid = { artifact =>
        ArtifactModel.create(projectId, artifact) map { result =>
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

  def delete(projectId: Long, id: String) = Action {
    ArtifactModel.deleteById(projectId, id)
    NoContent
  }

  def index(projectId: Long, offset: Option[Int] = None, date: Option[String]) = Action {

    // This kinda sucks. Not happy with this. Different URLs?
    if(date.isDefined) {
      Try {
        dateFormat.parseDateTime(date.get)
      } map { dt =>
        Ok(Json.toJson(ArtifactModel.getByDate(projectId, dt)))
      } getOrElse {
        BadRequest(Json.obj(
          "message" -> "Bad date format, please use ISO 8601"
        ))
      }
    } else if(offset.isDefined) {
      Ok(Json.toJson(ArtifactModel.getByIndex(projectId, offset.get)))
    } else {
      Ok(Json.toJson(ArtifactModel.getAllForProject(projectId)))
    }
  }

  def item(projectId: Long, id: String) = Action {
    ArtifactModel.getById(projectId, id) map { project =>
      Ok(Json.toJson(project))
    } getOrElse(NotFound)
  }

  def latest(projectId: Long) = Action {
    ArtifactModel.getLatest(projectId) map { project =>
      Ok(Json.toJson(project))
    } getOrElse(NotFound)
  }
}
