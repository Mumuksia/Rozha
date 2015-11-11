package models

import java.util.Date
import play.api.db._
import play.api.Play._
import anorm._
import anorm.SqlParser._

case class Participation(id: Int, name: String, status: String)

object Participation{
  
  val noteParser: RowParser[Participation] = (
    SqlParser.int("id") ~
    SqlParser.str("name") ~
    SqlParser.str("status")
  ) map {
      case id ~ name ~ status =>
        Participation(id, name, status)
    }
    
  val allRowsParser: ResultSetParser[Participation] = noteParser.single
  
  val allRowsParserOption: ResultSetParser[Option[Participation]] = noteParser.singleOpt
  
  val allRowsListParser: ResultSetParser[List[Participation]] = noteParser.*

  def findAll(): Seq[Participation] = {
    DB.withConnection { implicit c =>
      SQL("select * from Participation").
      as(allRowsListParser)
    }
  }
  
  def findAllByStatus(status: String): Seq[Participation] = {
    DB.withConnection { implicit c =>
      SQL("select * from Participation where status = {status}").
      on('status -> status).
      as(allRowsListParser)
    }
  }
  
  def closeAllOpen(): Seq[Participation] = {
    DB.withConnection { implicit c =>
      SQL("select * from Participation where status = {status}").
      on('status -> status).
      as(allRowsListParser)
    }
  }

  def create(participation: Participation) {
    DB.withConnection { implicit c =>
      SQL("insert into Participation(name, status) values ({name}, {status})").
        on( 'name -> participation.name, 'status -> participation.status).
        executeInsert()
    }
  }
  
  def create(name: String, status: String) {
    DB.withConnection { implicit c =>
      SQL("insert into Participation(name, status) values ({name}, {status})").
        on( 'name -> name, 'status -> status).
        executeInsert()
    }
  }
}
