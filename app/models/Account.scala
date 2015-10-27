package models

import services.FileService
import play.api.libs.json.{JsArray, Json, JsValue, Reads}
import play.api.db._
import play.api.Play._
import anorm._
import anorm.SqlParser._

case class Account(id: Int, email: String, password: String, name: String, role: Role)

object Account {
  
    val accountParser: RowParser[Account] = (
    SqlParser.int("id") ~
    SqlParser.str("name") ~
    SqlParser.str("email") ~
    SqlParser.str("password") ~
    SqlParser.str("role")
  ) map {
      case id ~ name ~ email ~ password ~ role =>
        Account(id, email, password, name, Role.valueOf(role))
    }

  val allRowsParser: ResultSetParser[Account] = accountParser.single
  
  val allRowsListParser: ResultSetParser[List[Account]] = accountParser.*
  
  
  val fileService = new FileService

  def authenticate(email: String, password: String): Option[Account] = {
    val acc = findByEmail(email)
    println(email)
    println(acc)
    acc
  }

  def findByEmail(email: String): Option[Account] = {
    val accs = findAllDB
    println(accs)
    findAllDB.find(account => account.email == email)
  }

  def findById(id: Int): Option[Account] = {
    findAllDB.find(account => account.id == id)    
  }
  
 def findAll(): Seq[Account] = {    
   val jsUsers : Seq[JsValue] = Json.parse(fileService.readFromFile("users.txt")).as[JsArray].value
   for(jsValue <- jsUsers) yield createAccount(jsValue)
 }
 
  def findAllDB(): Seq[Account] = {
    DB.withConnection { implicit c =>
      SQL("select * from ACCOUNT").
      as(allRowsListParser)
    }
  }
  
  def create(account: Account) {
   
  }
  
  def createAccount(jsValue: JsValue): Account ={
    Account((jsValue \ "id").as[String].toInt, (jsValue \ "email").as[String], (jsValue \ "password").as[String], 
            (jsValue \ "name").as[String], Role.valueOf((jsValue \ "role").as[String]))
  }
  
}
