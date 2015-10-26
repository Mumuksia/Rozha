package controllers

import java.util.UUID

import models.Reservations
import play.api.libs.json.{ JsArray, Json , JsObject}
import play.api.mvc._
import services.DBService
import services.FileService
import services.JsonService
import play.api.db._
import play.api.Play.current

class Application extends Controller {

  val fileService = new FileService
  val jsonService = new JsonService
  val wishFile = "wishes.txt"
  val dbService = new DBService

  // serves the web page
  def index = Action {    
    Ok(views.html.rozhaRead())
  }

  def loadData = Action {   
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatus("some")))
  }

  def addWish(name: String, number: String) = Action {
    Reservations.create(new Reservations(11, name, number, "temporary"))
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatus("temporary")))
  }

  def loadWishes = Action {
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatus("temporary")))
  }

}