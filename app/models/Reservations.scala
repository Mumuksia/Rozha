package models

import play.api.db._
import play.api.Play._
import anorm._
import anorm.SqlParser._

case class Reservations(id: Int, name: String, number: String, status: String, warId: Int)

object Reservations {

  val reservationsParser: RowParser[Reservations] = (
    SqlParser.int("id") ~
    SqlParser.str("name") ~
    SqlParser.str("number") ~
    SqlParser.str("status") ~
    SqlParser.int("warId")
  ) map {
      case id ~ name ~ number ~ status ~ warId =>
        Reservations(id, name, number, status, warId)
    }

  val allRowsParser: ResultSetParser[Reservations] = reservationsParser.single
  
  val allRowsListParser: ResultSetParser[List[Reservations]] = reservationsParser.*

  def findById(id: Int): Reservations = {
    DB.withConnection { implicit c =>
      SQL("select * from RESERVATIONS where id = {id}").on('id->id).as(allRowsParser)
    }
  }
  
  def findByStatus(status: String): Seq[Reservations] = {
    DB.withConnection { implicit c =>
      SQL("select * from RESERVATIONS where status = {stat}").on('stat->status).
      as(allRowsListParser)
    }
  }

  def findByStatusAndWar(status: String, warId: Int): Seq[Reservations] = {
    DB.withConnection { implicit c =>
      SQL("select * from RESERVATIONS where status = {stat} and warId = {warId}").on('stat->status, 'warId->warId).
      as(allRowsListParser)
    }
  }
  
  def findAll(): Seq[Reservations] = {
      null
  }

  def create(reservation: Reservations) {
    DB.withConnection { implicit c =>
      SQL("insert into RESERVATIONS(name, number, status, warId) values ({name}, {number}, {status}, {warId})").
        on( 'name -> reservation.name, 'number -> reservation.number, 'status -> reservation.status, 'warId -> reservation.warId).
        executeInsert()
    }
  }

  def createSample() {
  }

  def clearAll(status: String) {
    DB.withConnection { implicit c =>
      SQL("delete from RESERVATIONS where status = {status}").on('status -> status)
        .executeUpdate()
    }
  }
  
    def clearAllTable() {
    DB.withConnection { implicit c =>
      SQL("delete from RESERVATIONS where status != stay")
        .executeUpdate()
    }
  }
  
  def delete(id: Int){
     DB.withConnection { implicit c =>
      SQL("delete from RESERVATIONS where id = {id}").on('id -> id)
        .executeUpdate()
    }
  }

}
