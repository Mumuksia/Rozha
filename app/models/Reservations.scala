package models

import play.api.db._
import play.api.Play._
import anorm._
import anorm.SqlParser._

case class Reservations(id: Int, name: String, number: String, status: String)

object Reservations {

  val reservationsParser: RowParser[Reservations] = (
    SqlParser.int("id") ~
    SqlParser.str("name") ~
    SqlParser.str("number") ~
    SqlParser.str("status")
  ) map {
      case id ~ name ~ number ~ status =>
        Reservations(id, name, number, status)
    }

  val allRowsParser: ResultSetParser[Reservations] = reservationsParser.single
  
  val allRowsListParser: ResultSetParser[List[Reservations]] = reservationsParser.*

  def findById(id: Int): Reservations = {
    DB.withConnection { implicit c =>
      SQL("select * from RESERVATIONS where id = 1").as(allRowsParser)
    }
  }
  
  def findByStatus(status: String): Seq[Reservations] = {
        DB.withConnection { implicit c =>
      SQL("select * from RESERVATIONS where status = {stat}").on('stat->status).
      as(allRowsListParser)
    }
  }

  def findAll(): Seq[Reservations] = {
    null
  }

  def create(reservation: Reservations) {
    DB.withConnection { implicit c =>
      SQL("insert into Reservations(id, name, number, status) values ({id}, {name}, {number}, {status})").
        on('id -> reservation.id, 'name -> reservation.name, 'number -> reservation.number, 'status -> reservation.status).
        executeInsert()
    }
  }

  def createSample() {
    DB.withConnection { implicit c =>
      SQL("insert into Reservations(id, name, number, status) values ({id}, {name}, {number}, {status})").
        on('id -> 3, 'name -> "Cambridge", 'number -> "New", 'status -> "some").
        executeInsert()
    }
  }

  def clearAll(status: String) {
    DB.withConnection { implicit c =>
      SQL("delete from Reservations where status = {status}").on('status -> status)
        .executeUpdate()
    }
  }
  
    def clearAllTable() {
    DB.withConnection { implicit c =>
      SQL("delete from Reservations where status != stay")
        .executeUpdate()
    }
  }

}
