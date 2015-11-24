package models

import java.util.Date
import play.api.db._
import play.api.Play._
import anorm._
import anorm.SqlParser._

case class Participation(id: Int, name: String, status: String, dayofweek: String, weeknumber: String)

object Participation{
  
  val noteParser: RowParser[Participation] = (
    SqlParser.int("id") ~
    SqlParser.str("name") ~
    SqlParser.str("dayofweek") ~
    SqlParser.str("weeknumber") ~
    SqlParser.str("status")
  ) map {
      case id ~ name ~ status ~ weeknumber ~ dayofweek =>
        Participation(id, name, status, dayofweek, weeknumber)
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
  
    def findAllByStatusAndWeekAndDay(status: String, week: String, day: String): Seq[Participation] = {
    DB.withConnection { implicit c =>
      SQL("select * from Participation where status = {status} and dayofweek = {day} and weeknumber = {week}").
      on('status -> status, 'day->day, 'week->week).
      as(allRowsListParser)
    }
  }
  
  def closeAllOpen(): Seq[Participation] = {
    DB.withConnection { implicit c =>
      SQL("UPDATE Participation SET status = 'closed' WHERE status = 'new'").
      as(allRowsListParser)
    }
  }

  def create(participation: Participation) {
    DB.withConnection { implicit c =>
      SQL("insert into Participation(name, status, dayofweek, weeknumber) values ({name}, {status}, {dayofweek}, {weeknumber})").
        on( 'name -> participation.name, 'status -> participation.status, 'dayofweek -> participation.dayofweek, 'weeknumber -> participation.dayofweek).
        executeInsert()
    }
  }
  
  def create(name: String, status: String, week: String, day: String) {
    DB.withConnection { implicit c =>
      SQL("insert into Participation(name, status, dayofweek, weeknumber) values ({name}, {status}, {dayofweek}, {weeknumber})").
        on( 'name -> name, 'status -> status, 'dayofweek -> day, 'weeknumber -> week).
        executeInsert()
    }
  }
}
