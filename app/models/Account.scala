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
  
  val allRowsParserOption: ResultSetParser[Option[Account]] = accountParser.singleOpt
  
  val allRowsListParser: ResultSetParser[List[Account]] = accountParser.*
  
  
  val fileService = new FileService

  def authenticate(email: String, password: String): Option[Account] = {
    findByEmail(email)
  }

  def findByEmail(email: String): Option[Account] = {
     DB.withConnection { implicit c =>
      SQL("select * from ACCOUNT where email = {email}").on('email->email).
      as(allRowsParserOption)
    }
  }

  def findById(id: Int): Option[Account] = {
     DB.withConnection { implicit c =>
      SQL("select * from ACCOUNT where id = {id}").on('id->id).
      as(allRowsParserOption)
    }   
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
