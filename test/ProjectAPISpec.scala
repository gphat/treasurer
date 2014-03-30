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
      contentAsString(result.get) must contain("KO")
      status(result.get) must equalTo(400)
    }

    "send 201 on a good post" in new WithApplication {
      val result = route(FakeRequest(
        POST,
        "/1.0/project",
        FakeHeaders(),
        Json.obj(
          "name" -> "poopButt"
        )
      ))
      result must beSome
      status(result.get) must equalTo(201)
    }
  }
}
