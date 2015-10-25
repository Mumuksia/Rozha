package controllers

import java.util.UUID

import models.Reservation
import play.api.libs.json.{JsArray, Json}
import play.api.mvc._
import services.FileService
import services.JsonService

class Application extends Controller{

  val fileService = new FileService  
  val jsonService = new JsonService
  val wishFile = "wishes.txt"

  // serves the web page
  def index = Action {
    //println(Reservation.findById(1))
    Ok(views.html.rozhaRead())
  }
 
  def loadData = Action{
    Ok(fileService.readFromFile("data.txt"))
  }

  def addWish(name: String, number: String) = Action{
    val jsRow = jsonService.transformToWishRow(name, number)
    val jsArray: JsArray = Json.parse(fileService.readFromFile(wishFile)).as[JsArray]  
    val newArray: JsArray = jsArray.append(jsRow)
    fileService.updateListInFile(newArray.toString, wishFile)
    Ok(newArray)
  }
  
  def loadWishes = Action{
    Ok(fileService.readFromFile(wishFile))
  }
}