package models

import org.joda.time.DateTime

case class Artifact(
  id: String,
  version: String,
  dateCreated: DateTime = new DateTime(),
  dateInternal: DateTime = new DateTime()
)

object ArtifactModel {

}
