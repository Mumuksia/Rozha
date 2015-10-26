package controllers

import java.util.UUID

import models.Reservations
import play.api.libs.json.{ JsArray, Json , JsObject}
import play.api.mvc._
import services.DBService
import services.FileService
import services.JsonService
import play.api.db._
import play.api.Play.current

class Application extends Controller {

  val fileService = new FileService
  val jsonService = new JsonService
  val wishFile = "wishes.txt"
  val dbService = new DBService

  // serves the web page
  def index = Action {    
    Ok(views.html.rozhaRead())
  }

  def loadData = Action {   
    val jsData = Reservations.findByStatus("some").map( x => jsonService.transformToReservationRow(x)):Seq[JsObject]
    Ok(jsData.foldLeft(JsArray())((acc, x) => acc ++ Json.arr(x)))
  }

  def addWish(name: String, number: String) = Action {
    val jsRow = jsonService.transformToWishRow(name, number)
    val jsArray: JsArray = Json.parse(fileService.readFromFile(wishFile)).as[JsArray]
    val newArray: JsArray = jsArray.append(jsRow)
    fileService.updateListInFile(newArray.toString, wishFile)
    Ok(newArray)
  }

  def loadWishes = Action {
    Ok(fileService.readFromFile(wishFile))
  }

}