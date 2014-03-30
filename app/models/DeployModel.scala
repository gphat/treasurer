package models

import org.joda.time.DateTime

case class Deploy(
  id: Option[Long] = None,
  device: String,
  artifactId: String,
  dateCreated: Option[DateTime] = Some(new DateTime()),
  dateInternal: Option[DateTime] = Some(new DateTime())
)

object DeployModel {

}
