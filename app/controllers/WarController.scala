package controllers

import models.{Reservations, War}
import play.api.mvc._
import services.JsonService

class WarController extends Controller {
  val jsonService = new JsonService


  def loadReservations = Action {
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("some", War.getCurrentWarId).sortWith(_.number.toInt < _.number.toInt)))
  }

  def addReservation(name: String, number: String, approvedBy: String) = Action { request =>
    Reservations.create(new Reservations(0, name, number, "some", War.getCurrentWarId, request.remoteAddress))
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("some", War.getCurrentWarId)))
  }

  def startWar(name: String, note: String) = Action {
    War.closeCurrent(War.getCurrentWarId)
    War.createCurrent(name, note)
    Ok(jsonService.transformWarToJsObject(War.findCurrentWar))
  }

}
