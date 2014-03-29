package models

import org.joda.time.DateTime

case class Deploy(
  id: String,
  device: String,
  artifactId: String,
  dateCreated: DateTime,
  dateInternal: DateTime
)

object DeployModel {

}
