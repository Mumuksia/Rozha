package models

import java.util.Date
import play.api.db._
import play.api.Play._
import anorm._
import anorm.SqlParser._

case class Notes(id: Int, name: String, description: String, status: String, addedby: String)

object Notes{
  
  val noteParser: RowParser[Notes] = (
    SqlParser.int("id") ~
    SqlParser.str("name") ~
    SqlParser.str("description") ~
    SqlParser.str("status") ~
    SqlParser.str("addedby")
  ) map {
      case id ~ name ~ description ~ status ~ addedby =>
        Notes(id, name, description, status, addedby)
    }
    
  val allRowsParser: ResultSetParser[Notes] = noteParser.single
  
  val allRowsParserOption: ResultSetParser[Option[Notes]] = noteParser.singleOpt
  
  val allRowsListParser: ResultSetParser[List[Notes]] = noteParser.*

  def findAll(): Seq[Notes] = {
    DB.withConnection { implicit c =>
      SQL("select * from Notes").
      as(allRowsListParser)
    }
  }
  
  def findAllForUser(name: String): Seq[Notes] = {
    DB.withConnection { implicit c =>
      SQL("select * from Notes where name = {name}").
      on('name -> name).
      as(allRowsListParser)
    }
  }

  def create(note: Notes) {
    DB.withConnection { implicit c =>
      SQL("insert into NOTES(name, description, status, addedby) values ({name}, {description}, {status}, {addedby})").
        on( 'name -> note.name, 'description -> note.description, 'status -> note.status,
        'addedby -> note.addedby).
        executeInsert()
    }
  }
  
  def create(name: String, description: String, status: String, addedBy: String) {
    DB.withConnection { implicit c =>
      SQL("insert into NOTES(name, description, status, addedby) values ({name}, {description}, {status}, {addedby})").
        on( 'name -> name, 'description -> description, 'status -> status,
        'addedby -> addedBy).
        executeInsert()
    }
  }

  
}
  