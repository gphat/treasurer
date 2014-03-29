package models

import org.joda.time.DateTime

case class Artifact(
  id: String,
  version: String,
  dateCreated: DateTime,
  dateInternal: DateTime
)

object ArtifactModel {

}
