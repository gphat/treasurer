package util

import models._
import play.api.i18n.Messages
import play.api.libs.json.Json._
import play.api.libs.json._

object JsonFormats {

  implicit object ArtifactFormat extends Format[Artifact] {
    def reads(json: JsValue): JsResult[Artifact] = {
      JsSuccess(Artifact(
        id = (json \ "id").as[String]
      ))
    }

    def writes(obj: Artifact): JsValue = {
      toJson(Map(
        "id" -> JsString(obj.id)
      ))
    }
  }

  implicit object DeployFormat extends Format[Deploy] {
    def reads(json: JsValue): JsResult[Deploy] = {
      JsSuccess(Deploy(
        id = (json \ "id").as[String]
      ))
    }

    def writes(obj: Deploy): JsValue = {
      toJson(Map(
        "id" -> JsString(obj.id)
      ))
    }
  }

implicit object ProjectFormat extends Format[Project] {
    def reads(json: JsValue): JsResult[Project] = {
      JsSuccess(Project(
        id = (json \ "id").as[String]
      ))
    }

    def writes(obj: Project): JsValue = {
      toJson(Map(
        "id" -> JsString(obj.id)
      ))
    }
  }
}
