package models

import services.FileService
import play.api.libs.json.{JsArray, Json, JsValue, Reads}
import play.api.db._
import play.api.Play._
import anorm._
import anorm.SqlParser._

case class Account(id: Int, email: String, password: String, name: String, role: Role)

object Account {
  
  
  val fileService = new FileService

  def authenticate(email: String, password: String): Option[Account] = {
    findByEmail(email)
  }

  def findByEmail(email: String): Option[Account] = {
    findAll.find(account => account.email == email)
  }

  def findById(id: Int): Option[Account] = {
    findAll.find(account => account.id == id)    
  }
  
 def findAll(): Seq[Account] = {    
   val jsUsers : Seq[JsValue] = Json.parse(fileService.readFromFile("users.txt")).as[JsArray].value
   for(jsValue <- jsUsers) yield createAccount(jsValue)
 }
 
  def findAllDB(): Seq[Account] = {
    null
  }
  
  def create(account: Account) {
   
  }
  
  def createAccount(jsValue: JsValue): Account ={
    Account((jsValue \ "id").as[String].toInt, (jsValue \ "email").as[String], (jsValue \ "password").as[String], 
            (jsValue \ "name").as[String], Role.valueOf((jsValue \ "role").as[String]))
  }
  
}
