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
        "/1.0/project",
        FakeHeaders(),
        "some text"
      ))
      result must beSome
      status(result.get) must equalTo(400)
    }

    "send 400 on a malformed json post" in new WithApplication {
      val result = route(FakeRequest(
        POST,
        "/1.0/project",
        FakeHeaders(),
        Json.obj(
          "poop" -> "butt"
        )
      ))
      result must beSome
      status(result.get) must equalTo(400)
    }

    "send proper codes for create and delete" in new WithApplication {
      // Make a project
      val result = route(FakeRequest(
        POST,
        "/1.0/project",
        FakeHeaders(),
        Json.obj(
          "name" -> "poopButt"
        )
      ))

      // Verify we got one back
      result must beSome.which(status(_) == 201)
      val proj = Json.fromJson[Project](contentAsJson(result.get))
      proj.asOpt must beSome

      // Delete it
      route(FakeRequest(
        DELETE,
        "/1.0/project/" + proj.asOpt.get.id.get,
        FakeHeaders(),
        Json.obj(
          "name" -> "poopButt"
        )
      )) must beSome.which(status(_) == 204) // Make sure we get a no content
    }
  }
}
