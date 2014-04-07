import models._

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class DeployModelSpec extends Specification {
  sequential
  "Deploy Model" should {

    "create and delete deploys" in new WithApplication{
      val proj = Project(
        name = "Poop"
      )

      val project = ProjectModel.create(proj).get

      val art = Artifact(
        id = "abc123",
        version = "1.2.3",
        url = "http://www.example.com/poop.zip"
      )

      // Make artifact!
      val artifactResult = ArtifactModel.create(project.id.get, art)
      artifactResult must beSome
      println(artifactResult.get.id)

      val obj = Deploy(
        device = "somehost",
        artifactId = art.id
      )

      // Make deploy!
      val createResult = DeployModel.create(project.id.get, obj)
      createResult must beSome

      // Clean up!
      DeployModel.deleteById(project.id.get, createResult.get.id.get)

      val oid = artifactResult.get.id
      ArtifactModel.deleteById(project.id.get, oid)

      // Make sure it's gone
      ArtifactModel.getById(project.id.get, oid) must beNone

      ProjectModel.deleteById(project.id.get)
    }
  }
}
