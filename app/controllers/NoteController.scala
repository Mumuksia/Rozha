package controllers

import play.api.libs.json.{JsArray, Json}
import play.api.mvc._
import services.FileService
import services.JsonService

class NoteController extends Controller{  

val fileService = new FileService
val jsonService = new JsonService

  val notesFile = "notes.txt"

  def initFile = Action {
    fileService.saveJsArrayToFile(jsonService.transformNoteToJsonRow("SampleName", "note", "noteBy").as[JsArray], notesFile)
    Ok(jsonService.transformToReservationRow("SampleName", "777", "approvedBy"))
  }

  def loadNotes = Action{
    Ok(Json.parse(fileService.readFromFile(notesFile)))            
  }
  
  def addNote(name: String, note: String, noteBy: String) = Action{
    val jsRow = jsonService.transformNoteToJsonRow(name, note, noteBy)
    val jsArray: JsArray = Json.parse(fileService.readFromFile(notesFile)).as[JsArray]  
    val newArray: JsArray = jsArray.append(jsRow)
    fileService.updateListInFile(newArray.toString, notesFile)
    Ok(newArray)
  }

}
