package controllers

import models.Notes
import models.Participation
import play.api.libs.json.{JsArray, Json}
import play.api.mvc._
import services.FileService
import services.JsonService

class ParticipationController extends Controller {
  
val jsonService = new JsonService

  val notesFile = "notes.txt"

  def initFile = Action {
    Ok(jsonService.transformToReservationRow("SampleName", "777", "approvedBy"))
  }

  def loadParticipants = Action{
    Ok(jsonService.transformParticipationsToJsArray(Participation.findAll))            
  }
  
  def addParticipant(name: String) = Action{
    Participation.create(name, "new")
    Ok(jsonService.transformParticipationsToJsArray(Participation.findAll))
  }
}


