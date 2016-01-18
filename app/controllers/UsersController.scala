package controllers

import play.api.mvc._
import java.io.ByteArrayInputStream
import java.io.File
import services.DBService
import services.ExcelImporter
import services.FileService
import services.JsonService
import play.api.db._
import models.Notes
import models.Reservations
import models.Role.Administrator
import models.Role.NormalUser
import models.User
import models.War
import play.api.Play.current
import services.Pjax
import services.UserDTO
import java.io.FileInputStream
import jp.t2v.lab.play2.auth.AuthConfig
import jp.t2v.lab.play2.auth.AuthElement
import jp.t2v.lab.play2.stackc.StackableController
import views.html
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.DateUtil

class UsersController   extends Controller with Pjax with AuthElement with AuthConfigImpl {
  
  val jsonService = new JsonService
  val excelImporter = new ExcelImporter
  var uName: String = "Muksia"
  
def loadUsers = Action {
  request => 
  Ok(jsonService.transformUsersDTOAndPenaltiesToJsObject(UserDTO.getAllUsersDTO(Notes.findAll, User.findAll), Notes.findAllForUser(uName)))
}  

def addUser(clanId: String, name: String, status: String) = Action{
    User.create(clanId, name, status, "some")
    uName = name
    Ok(jsonService.transformUsersDTOAndPenaltiesToJsObject(UserDTO.getAllUsersDTO(Notes.findAll, User.findAll), Notes.findAllForUser(name)))
}

def loadUsersWithUser(userName: String) = Action{
      request => 
      uName = userName
  Ok(jsonService.transformUsersDTOAndPenaltiesToJsObject(UserDTO.getAllUsersDTO(Notes.findAll, User.findAll), Notes.findAllForUser(userName)))
}

  def syncUsers = Action(parse.temporaryFile) { implicit request =>   
  val result = excelImporter.parse("C:/work/CoC3.xlsx")    
  models.User.createLightUsers(result.map(v=>parseUser(v)))    
  Ok(jsonService.transformUsersDTOAndPenaltiesToJsObject(UserDTO.getAllUsersDTO(Notes.findAll, User.findAll), Notes.findAllForUser(uName)))
} 

  def parseUser(v: Vector[String]) : models.User ={
    if (!v(1).isEmpty){
      new models.User(1, v(1), v(2), "some", "some")
    } else {
      null
    }
  }
    
  
  
  protected val fullTemplate: User => Template = html.fullTemplate.apply
}

