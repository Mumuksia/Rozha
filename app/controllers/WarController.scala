package controllers

import models.Reservations
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
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatus("some")))            
  }
  
  def deleteReservation(id: String) = Action{
    println(id)
    Ok(Json.parse(fileService.readFromFile(reservationFile)))            
  }
  
  def addReservation(name: String, number: String, approvedBy: String) = Action{
    Reservations.create(new Reservations(0, name, number, "some"))
    Ok(jsonService.transformReservationsToJsArray(Reservations.findByStatus("some")))    
  }

}
