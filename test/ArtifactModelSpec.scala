import models._

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class ArtifactModelSpec extends Specification {
  sequential
  "Artifact Model" should {

    "create and delete artifacts" in new WithApplication{
      val proj = Project(
        name = "Poop"
      )

      val project = ProjectModel.create(proj).get

      val obj = Artifact(
        id = "abc123",
        version = "1.2.3",
        url = "http://www.example.com/poop.zip"
      )

      // Make one!
      val createResult = ArtifactModel.create(project.id.get, obj)
      createResult must beSome

      // Count 'em
      val allOfEm = ArtifactModel.getAllForProject(project.id.get)
      allOfEm.length must beEqualTo(1)

      // Latest?
      val latest = ArtifactModel.getLatest(project.id.get)
      latest must beSome
      latest.get.id must beEqualTo("abc123")

      // Clean up!
      val oid = createResult.get.id
      ArtifactModel.deleteById(project.id.get, oid)

      // Make sure it's gone
      ArtifactModel.getById(project.id.get, oid) must beNone

      ProjectModel.deleteById(project.id.get)
    }
  }
}
