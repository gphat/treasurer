import models._

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class ProjectModelSpec extends Specification {

  "Project Model" should {

    "create and delete projects" in new WithApplication{
      val obj = Project(name = "TestProject")

      // Make one!
      val createResult = ProjectModel.create(obj)
      createResult must beSome

      // Count 'em
      val allOfEm = ProjectModel.getAll
      allOfEm.length must beEqualTo(1)

      // Clean up!
      val oid = createResult.get.id.get
      ProjectModel.deleteById(oid)

      // Make sure it's gone
      ProjectModel.getById(oid) must beNone
    }
  }
}
