package models

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
  
  def findAllDB(): Seq[Account] = {
    DB.withConnection { implicit c =>
      SQL("select * from ACCOUNT where role != 'Administrator'").
      as(allRowsListParser)
    }
  }
  
  def updateRoleAccount(email: String, role: String) = {      
    DB.withConnection { implicit c =>
      SQL("UPDATE Account SET role = {role} WHERE email = {email}").
      on('role->role, 'email->email).
      executeUpdate()
      
    }
  }
  
  def deleteAccountById(email: String) = {
        DB.withConnection { implicit c =>
      SQL("delete from Account where email = {email}").on('email -> email)
        .executeUpdate()
    }
  }
 
    def create(name: String, email: String, password: String, role: String){
    DB.withConnection { implicit c =>
      SQL("insert into ACCOUNT(name, email, password, role) values ({name}, {email}, {password}, {role})").
        on( 'name -> name, 'email -> email, 'password -> password,
        'role -> role).
        executeInsert()
    }
  }
  

}
