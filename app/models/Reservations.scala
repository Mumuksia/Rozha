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
  
  def findById(id: Int): Reservations = {   
    DB.withConnection { implicit c =>
    SQL("select * from RESERVATIONS where id = 1").as(allRowsParser)
    }
  }
    
 def findAll(): Seq[Reservations] = {    
   null
 }
  
  def create(reservation: Reservations) {

  }
  
}
