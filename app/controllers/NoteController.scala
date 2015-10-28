package controllers

import models.Notes
import play.api.libs.json.{JsArray, Json}
import play.api.mvc._
import services.FileService
import services.JsonService

class NoteController extends Controller{  

val fileService = new FileService
val jsonService = new JsonService

  val notesFile = "notes.txt"

  def initFile = Action {
    Ok(jsonService.transformToReservationRow("SampleName", "777", "approvedBy"))
  }

  def loadNotes = Action{
    Ok(jsonService.transformNotesToJsArray(Notes.findAll))            
  }
  
  def addNote(name: String, descrption: String, addedBy: String) = Action{
    Notes.create(name, descrption, "status", addedBy)
    Ok(jsonService.transformNotesToJsArray(Notes.findAll))
  }

}
