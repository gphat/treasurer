package controllers.api

import models._
import play.api._
import play.api.libs.json.{Json,JsError}
import play.api.mvc._
import util.JsonFormats._

object Artifact extends Controller {

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

  def index = Action {
    Ok(Json.toJson(ProjectModel.getAll))
  }

  def item(projectId: Long, id: String) = Action {
    ArtifactModel.getById(projectId, id) map { project =>
      Ok(Json.toJson(project))
    } getOrElse(NotFound)
  }
}
