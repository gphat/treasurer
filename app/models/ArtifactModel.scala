package models

import java.io.File
import org.joda.time.DateTime
import play.api.Play.current

case class Artifact(
  id: String,
  url: String,
  dateCreated: Option[DateTime] = Some(new DateTime())
)

object ArtifactModel {

  def create(projectId: String, id: String, file: File): Option[Artifact] = {

    S3Model.createArtifact(projectId, id, file)
    Some(Artifact(id = id, url = "http://www.example.com"))
  }

  /**
   * Delete artifact.
   */
  def deleteById(projectId: String, id: String) {
    S3Model.deleteArtifact(projectId, id)
  }

  /**
   * Get all artifacts.
   */
  def getAllForProject(projectId: String): Seq[Artifact] = {

    S3Model.getArtifacts(projectId)
  }

  /**
   * Get an artifact by id.
   */
  def getById(projectId: String, id: String): Option[Artifact] = {

    S3Model.getArtifact(projectId, id)
  }
}
