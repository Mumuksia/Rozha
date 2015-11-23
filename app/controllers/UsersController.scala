package controllers

import play.api.mvc._
import services.DBService
import services.FileService
import services.JsonService
import play.api.db._
import models.Reservations
import models.User
import models.War
import play.api.Play.current

class UsersController  extends Controller{
  
  val jsonService = new JsonService

def loadUsers = Action {
  request => 
  Ok(jsonService.transformUsersToJsArray(User.findAll))
}  

def addUser(clanId: String, name: String, status: String) = Action{
    User.create(clanId, name, status, "some")
    Ok(jsonService.transformUsersToJsArray(User.findAll))
}
  
}

