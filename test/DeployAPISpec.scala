import models._
import org.joda.time.format.ISODateTimeFormat
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._
import util.JsonFormats._

@RunWith(classOf[JUnitRunner])
class DeployAPISpec extends Specification {

  val dateParser = ISODateTimeFormat.dateTimeParser();

  "Deploy API" should {

    "send proper codes for create and delete" in new WithApplication {

      val project = ProjectModel.create(Project(name = "Stankonia")).get
      val artifact = ArtifactModel.create(project.id.get, Artifact(
        id = "abc123",
        version =  "1.2.3",
        url = "http://www.example.com/poop.zip"
      )).get

      // Make an deploy
      val result = route(FakeRequest(
        POST,
        "/1.0/projects/" + project.id.get + "/deploys",
        FakeHeaders(),
        Json.obj(
          "device" -> "server1234",
          "artifactId" -> artifact.id
        )
      ))

      // Verify we got one back
      result must beSome.which(status(_) == 201)
      val dep = Json.fromJson[Deploy](contentAsJson(result.get)).get

      dep.id must beSome
      dep.device must beEqualTo("server1234")
      dep.artifactId must beEqualTo(artifact.id)

      // Verify we can fetch it
      route(FakeRequest(
        GET,
        "/1.0/projects/" + project.id.get + "/deploys/" + dep.id.get
      )) must beSome.which(status(_) == 200)

      // Delete it
      route(FakeRequest(
        DELETE,
        "/1.0/projects/" + project.id.get + "/deploys/" + dep.id.get
      )) must beSome.which(status(_) == 204) // Make sure we get a no content

      ArtifactModel.deleteById(project.id.get, "abc123")
      ProjectModel.deleteById(project.id.get)
    }
  }
}
