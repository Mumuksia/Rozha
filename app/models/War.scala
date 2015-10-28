package models

import java.util.Date
import play.api.db._
import play.api.Play._
import anorm._
import anorm.SqlParser._

case class War(id: Int, name: String, startdate: Date, score: String, status: String, note: String)

object War{
  
  val warParser: RowParser[War] = (
    SqlParser.int("id") ~
    SqlParser.str("name") ~
    SqlParser.date("startdate") ~
    SqlParser.str("score") ~
    SqlParser.str("status") ~
    SqlParser.str("note")
  ) map {
      case id ~ name ~ startdate ~ score ~ status ~ note =>
        War(id, name, startdate, score, status, note)
    }
    
  val allRowsParser: ResultSetParser[War] = warParser.single
  
  val allRowsParserOption: ResultSetParser[Option[War]] = warParser.singleOpt
  
  val allRowsListParser: ResultSetParser[List[War]] = warParser.*
  
  def findByStatus(status: String): Seq[War] = {
    DB.withConnection { implicit c =>
      SQL("select * from WAR where status = {stat}").on('stat->status).
      as(allRowsListParser)
    }
  }
  
  def findCurrentWar(): Option[War] = {
    DB.withConnection { implicit c =>
      SQL("select * from WAR where status = 'open'").
      as(allRowsParserOption)
    }
  }

  def findAll(): Seq[War] = {
    DB.withConnection { implicit c =>
      SQL("select * from WAR").
      as(allRowsListParser)
    }
  }

  def create(war: War) {
    DB.withConnection { implicit c =>
      SQL("insert into WAR(name, startdate, score, status, note) values ({name}, {startdate}, {score}, {status}, {note})").
        on( 'name -> war.name, 'startdate -> war.startdate, 'score -> war.score,
        'status -> war.status, 'note -> war.note).
        executeInsert()
    }
  }
  
  def update(war: War) {
    DB.withConnection { implicit c =>
      SQL("insert into WAR(name, startdate, score, status, note) values ({name}, {startdate}, {score}, {status}, {note})").
        on( 'name -> war.name, 'startdate -> war.startdate, 'score -> war.score,
        'status -> war.status, 'note -> war.note).
        executeInsert()
    }
  }
  
}
  

