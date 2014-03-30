package controllers.api.project

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

  def index(projectId: Long) = Action {
    Ok(Json.toJson(ArtifactModel.getAllForProject(projectId)))
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
