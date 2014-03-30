import models._
import util.JsonFormats._

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class ArtifactAPISpec extends Specification {

  "Artifact API" should {

    // "send 400 on a non-json post" in new WithApplication {
    //   val result = route(FakeRequest(
    //     POST,
    //     "/1.0/projects",
    //     FakeHeaders(),
    //     "some text"
    //   ))
    //   result must beSome
    //   status(result.get) must equalTo(400)
    // }

    // "send 400 on a malformed json post" in new WithApplication {
    //   val result = route(FakeRequest(
    //     POST,
    //     "/1.0/projects",
    //     FakeHeaders(),
    //     Json.obj(
    //       "poop" -> "butt"
    //     )
    //   ))
    //   result must beSome
    //   status(result.get) must equalTo(400)
    // }

    // "verify missing get" in new WithApplication {
    //   route(FakeRequest(
    //     GET,
    //     "/1.0/projects/123"
    //   )) must beSome.which(status(_) == 404)
    // }

    // "verify empty index" in new WithApplication {
    //   // Check index
    //   val index = route(FakeRequest(
    //     GET,
    //     "/1.0/projects"
    //   ))
    //   index must beSome
    //   status(index.get) must beEqualTo(200)
    //   contentAsString(index.get) must beEqualTo("[]")
    // }

    "send proper codes for create and delete" in new WithApplication {

      val project = ProjectModel.create(Project(name = "Stankonia")).get

      // Make an artifact
      val result = route(FakeRequest(
        POST,
        "/1.0/projects/" + project.id.get + "/artifacts",
        FakeHeaders(),
        Json.obj(
          "id" -> "abc123",
          "version" -> "1.2.3",
          "url" -> "http://www.example.com/poop.zip"
        )
      ))

      // Verify we got one back
      result must beSome.which(status(_) == 201)
      val art = Json.fromJson[Artifact](contentAsJson(result.get))
      art.asOpt must beSome

      // Check index
      val index = route(FakeRequest(
        GET,
        "/1.0/projects/" + project.id.get + "/artifacts"
      )) must beSome.which(contentAsString(_).contains("abc123"))

      // Get it by id
      val item = route(FakeRequest(
        GET,
        "/1.0/projects/" + project.id.get + "/artifacts/abc123"
      ))
      status(item.get) must beEqualTo(200)
      contentAsString(item.get) must contain("abc123")

      // Delete it
      route(FakeRequest(
        DELETE,
        "/1.0/projects/" + project.id.get + "/artifacts/abc123"
      )) must beSome.which(status(_) == 204) // Make sure we get a no content

      ProjectModel.deleteById(project.id.get)
    }
  }
}
