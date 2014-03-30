package models

import org.joda.time.DateTime

case class Artifact(
  id: String,
  version: String,
  dateCreated: Option[DateTime] = Some(new DateTime()),
  dateInternal: Option[DateTime] = Some(new DateTime())
)

object ArtifactModel {

}
