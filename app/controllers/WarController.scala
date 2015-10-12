package controllers

import java.util.UUID
import play.api.libs.json.{JsArray, Json}
import play.api.mvc._
import services.FileService
import services.JsonService

class WarController extends Controller{  

val fileService = new FileService
val jsonService = new JsonService

  def initFile = Action {
    fileService.saveReservation(jsonService.transformToReservationRow("SampleName", "777", "approvedBy"))
    Ok(jsonService.transformToReservationRow("SampleName", "777", "approvedBy"))
  }

  def loadReservations = Action{
    val out = Json.parse(fileService.readFromFile(fileService.reservationFile))        
    Ok(out)
  }
  
  def addReservation(name: String, number: String, approvedBy: String) = Action{
    val jsRow = jsonService.transformToReservationRow(name, number, approvedBy)
    val jsArray: JsArray = Json.parse(fileService.readFromFile(fileService.reservationFile)).as[JsArray]  
    val newArray: JsArray = jsArray.append(jsRow)
    println(newArray.toString)
    fileService.updateReservations(newArray.toString)
    Ok(jsRow)
  }

}
