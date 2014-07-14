import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import models._

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

import util.JsonFormats._

@RunWith(classOf[JUnitRunner])
class JsonStuffSpec extends Specification {
  sequential
  val dateFormatter = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss'Z'")

  "Json Formats" should {

    "handle Project" in {
      val theDate = new DateTime()
      val obj = Project(id = Some(1234), name = "Poop", dateCreated = Some(theDate))
      val jsonString = Json.toJson(obj).toString

      val deObj = Json.fromJson[Project](Json.parse(jsonString)).asOpt
      deObj must beSome
      deObj.get.id must beSome.which(_ == 1234)
      deObj.get.name must beEqualTo("Poop")
      dateFormatter.print(deObj.get.dateCreated.get) must beEqualTo(
        dateFormatter.print(theDate)
      )
    }

    "handle Artifact" in {
      val theDate = new DateTime()
      val obj = Artifact(
        id = "1234", version = "1.2.3",
        dateCreated = Some(theDate),
        url = "http://www.example.com/poop.zip"
      )
      val jsonString = Json.toJson(obj).toString

      val deObj = Json.fromJson[Artifact](Json.parse(jsonString)).asOpt
      deObj must beSome
      deObj.get.id must beEqualTo("1234")
      deObj.get.version must beEqualTo("1.2.3")
      deObj.get.url must beEqualTo("http://www.example.com/poop.zip")
      dateFormatter.print(deObj.get.dateCreated.get) must beEqualTo(
        dateFormatter.print(theDate)
      )
    }
  }
}
