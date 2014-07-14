package util

import models._
import org.joda.time.DateTime
import play.api.i18n.Messages

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object JsonFormats {

  val artifactReads: Reads[Artifact] = (
    (JsPath \ "id").read[String] and
    (JsPath \ "version").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "dateCreated").readNullable[DateTime] and
    (JsPath \ "dateInternal").readNullable[DateTime]
  )(Artifact.apply _)

  val artifactWrites: Writes[Artifact] = (
    (JsPath \ "id").write[String] and
    (JsPath \ "version").write[String] and
    (JsPath \ "url").write[String] and
    (JsPath \ "dateCreated").writeNullable[DateTime] and
    (JsPath \ "dateInternal").writeNullable[DateTime]
  )(unlift(Artifact.unapply))

  implicit val artifactFormat: Format[Artifact] = Format(
    artifactReads, artifactWrites
  )
}
