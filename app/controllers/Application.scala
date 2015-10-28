package controllers

import java.util.UUID

import models.Reservations
import play.api.libs.json.{ JsArray, Json , JsObject}
import play.api.mvc._
import services.DBService
import services.FileService
import services.JsonService
import play.api.db._
import models.War
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
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("some", getCurrentWarId)))
  }

  def addWish(name: String, number: String) = Action {
    Reservations.create(new Reservations(0, name, number, "temporary", getCurrentWarId))
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("temporary", getCurrentWarId)))
  }

  def loadWishes = Action {
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("temporary", getCurrentWarId)))
  }
  
  def loadCurrentWar = Action {
    Ok(jsonService.transformWarToJsObject(War.findCurrentWar))
  }
  
  def getCurrentWarId() : Int = {
    War.findCurrentWar() match {
      case Some(war) => war.id
      case None => 0
    }
  }

}