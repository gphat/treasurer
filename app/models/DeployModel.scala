package models

import org.joda.time.DateTime

case class Deploy(
  id: Option[Int] = None,
  device: String,
  artifactId: String,
  dateCreated: DateTime = new DateTime(),
  dateInternal: DateTime = new DateTime()
)

object DeployModel {

}
