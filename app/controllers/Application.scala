package controllers

import java.util.UUID

import models.Reservation
import play.api.libs.json.{JsArray, Json}
import play.api.mvc._
import services.FileService
import services.JsonService
import play.api.db._
import play.api.Play.current

class Application extends Controller{

  val fileService = new FileService  
  val jsonService = new JsonService
  val wishFile = "wishes.txt"

  // serves the web page
    def index = Action {
    var outString = "Number is "
    val conn = DB.getConnection()
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT name as testkey FROM RESERVATIONS where id = 1 ")
      while (rs.next()) {
        outString += rs.getString("testkey")
      }
    } finally {
      conn.close()
    }
    println(outString)
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