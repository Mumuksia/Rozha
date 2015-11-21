package models

import play.api.db._
import play.api.Play._
import anorm._
import anorm.SqlParser._

case class User(id: Int, name: String, clanId: String, status: String)

object User {
  
    val accountParser: RowParser[User] = (
    SqlParser.int("id") ~
    SqlParser.str("name") ~
    SqlParser.str("clanId") ~
    SqlParser.str("status")
  ) map {
      case id ~ name ~ clanId ~ status =>
        User(id, name, clanId, status)
    }

  val allRowsParser: ResultSetParser[User] = accountParser.single
  
  val allRowsParserOption: ResultSetParser[Option[User]] = accountParser.singleOpt
  
  val allRowsListParser: ResultSetParser[List[User]] = accountParser.*

  def findById(id: Int): Option[User] = {
     DB.withConnection { implicit c =>
      SQL("select * from User where id = {id}").on('id->id).
      as(allRowsParserOption)
    }   
  }

  def findAll(): Seq[User] = {
    DB.withConnection { implicit c =>
      SQL("select * from User").
      as(allRowsListParser)
    }
  }
  
  def create(account: User) {
   
  }
  
}
