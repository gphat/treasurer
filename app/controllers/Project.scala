package controllers

import play.api._
import play.api.mvc._

object Project extends Controller {

  def index = Action {
    Ok("ok")
  }
}
