import models._
import util.JsonFormats._

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class ProjectAPISpec extends Specification {

  "Project API" should {

    "send 400 on a non-json post" in new WithApplication {
      val result = route(FakeRequest(
        POST,
        "/1.0/projects",
        FakeHeaders(),
        "some text"
      ))
      result must beSome
      status(result.get) must equalTo(400)
    }

    "send 400 on a malformed json post" in new WithApplication {
      val result = route(FakeRequest(
        POST,
        "/1.0/projects",
        FakeHeaders(),
        Json.obj(
          "poop" -> "butt"
        )
      ))
      result must beSome
      status(result.get) must equalTo(400)
    }

    "verify missing get" in new WithApplication {
      route(FakeRequest(
        GET,
        "/1.0/projects/123"
      )) must beSome.which(status(_) == 404)
    }

    "verify empty index" in new WithApplication {
      // Check index
      val index = route(FakeRequest(
        GET,
        "/1.0/projects"
      ))
      index must beSome
      status(index.get) must beEqualTo(200)
      contentAsString(index.get) must beEqualTo("[]")
    }

    "send proper codes for create and delete" in new WithApplication {
      // Make a project
      val result = route(FakeRequest(
        POST,
        "/1.0/projects",
        FakeHeaders(),
        Json.obj(
          "name" -> "poopButt"
        )
      ))

      // Verify we got one back
      result must beSome.which(status(_) == 201)
      val proj = Json.fromJson[Project](contentAsJson(result.get))
      proj.asOpt must beSome

      // Check index
      val index = route(FakeRequest(
        GET,
        "/1.0/projects"
      )) must beSome.which(contentAsString(_).contains("poopButt"))

      // Get it by id
      val item = route(FakeRequest(
        GET,
        "/1.0/projects/" + proj.asOpt.get.id.get
      ))
      status(item.get) must beEqualTo(200)
      contentAsString(item.get) must contain("poopButt")

      // Delete it
      route(FakeRequest(
        DELETE,
        "/1.0/projects/" + proj.asOpt.get.id.get,
        FakeHeaders(),
        Json.obj(
          "name" -> "poopButt"
        )
      )) must beSome.which(status(_) == 204) // Make sure we get a no content
    }
  }
}
