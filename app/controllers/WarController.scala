package controllers

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
    Ok(Json.parse(fileService.readFromFile(reservationFile)))            
  }
  
  def deleteReservation(id: String) = Action{
    Ok(Json.parse(fileService.readFromFile(reservationFile)))            
  }
  
  def addReservation(name: String, number: String, approvedBy: String) = Action{
    val jsRow = jsonService.transformToReservationRow(name, number, approvedBy)
    val jsArray: JsArray = Json.parse(fileService.readFromFile(reservationFile)).as[JsArray]  
    val newArray: JsArray = jsArray.append(jsRow)
    fileService.updateListInFile(newArray.toString, reservationFile)
    Ok(newArray)
  }

}
