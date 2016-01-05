package controllers

import play.api.mvc._
import services.DBService
import services.FileService
import services.JsonService
import play.api.db._
import models.Notes
import models.Reservations
import models.User
import models.War
import play.api.Play.current
import services.UserDTO

class UsersController  extends Controller{
  
  val jsonService = new JsonService
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
  
}

