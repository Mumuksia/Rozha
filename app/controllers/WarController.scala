package controllers

import models.Participation
import models.Reservations
import models.War
import play.api.libs.json.{JsArray, Json}
import play.api.mvc._
import services.FileService
import services.JsonService

class WarController extends Controller{  

val fileService = new FileService
val jsonService = new JsonService
val reservationFile = "reservations.txt"

  def initFile = Action {
    fileService.saveJsArrayToFile(jsonService.transformToReservationRow("SampleName", "777", "approvedBy").as[JsArray], reservationFile)
    Ok(jsonService.transformToReservationRow("SampleName", "777", "approvedBy"))
  }

  def loadReservations = Action{
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("some", War.getCurrentWarId).sortWith(_.number.toInt < _.number.toInt)))            
  }
  
  def deleteReservation(id: String) = Action{
    Ok(Json.parse(fileService.readFromFile(reservationFile)))            
  }
  
  def addReservation(name: String, number: String, approvedBy: String) = Action{ request => 
    Reservations.create(new Reservations(0, name, number, "some", War.getCurrentWarId, request.remoteAddress))
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatusAndWar("some", War.getCurrentWarId)))    
  }
  
  def startWar(name: String, note: String) = Action {    
    War.closeCurrent(War.getCurrentWarId)
    War.createCurrent(name, note)
    Ok(jsonService.transformWarToJsObject(War.findCurrentWar))
  }
  
}
