package controllers

import models.{Reservations, War}
import play.api.mvc._
import services.JsonService

class Application extends Controller {

  val jsonService = new JsonService

  def index = Action {
    Ok(views.html.rozhaRead())
  }

  def loadData = Action {
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("some", War.getCurrentWarId)))
  }

  def addWish(name: String, number: String) = Action { request =>
    Reservations.create(new Reservations(0, name, number, "temporary", War.getCurrentWarId, request.remoteAddress))
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("temporary", War.getCurrentWarId)))
  }

  def loadWishes = Action {
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("temporary", War.getCurrentWarId)))
  }

  def deleteWish(id: String) = Action {
    Reservations.delete(id.toInt)
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("temporary", War.getCurrentWarId)))
  }

  def deleteWishByNumber(number: String) = Action {
    Reservations.delete(number.toInt)
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("some", War.getCurrentWarId)))
  }

  def loadCurrentWar = Action {
    Ok(jsonService.transformWarToJsObject(War.findCurrentWar))
  }

}
