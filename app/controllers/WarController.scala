package controllers

import java.util.UUID
import play.api.libs.json.{JsArray, Json}
import play.api.mvc._
import services.FileService
import services.JsonService

class WarController extends Controller{  

val fileService = new FileService
val jsonService = new JsonService

  val JSON_KEY_COMMENTS = "comments"
  val JSON_KEY_AUTHOR = "author"
  val JSON_KEY_TEXT = "text"
  val JSON_KEY_ID = "id"

  // Initialise the comments list
  var commentsJson: JsArray = Json.arr(
    Json.obj(JSON_KEY_ID -> UUID.randomUUID().toString, JSON_KEY_AUTHOR -> "Pete Hunt", JSON_KEY_TEXT -> "This is one comment"),
    Json.obj(JSON_KEY_ID -> UUID.randomUUID().toString, JSON_KEY_AUTHOR -> "Jordan Walke", JSON_KEY_TEXT -> "This is *another* comment")
  )
  
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
    fileService.saveReservation(jsRow)
    Ok(jsRow)
  }

}
