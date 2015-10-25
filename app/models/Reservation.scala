package models

import scalikejdbc._

case class Reservation(id: Int, name: String, number: String, status: String)

object Reservation extends SQLSyntaxSupport[Reservation]  {
  
  override val tableName = "RESERVATION"

  override val columns = Seq("ID", "NAME", "NUMBER", "STATUS")

  def apply(r: ResultName[Reservation])(rs: WrappedResultSet): Reservation = new Reservation(
    id = rs.int(r.id),
    name = rs.string(r.name),
    number = rs.string(r.number),
    status = rs.string(r.status)
  )

  val r = Reservation.syntax("r")
  
  private val auto = AutoSession
  
  def findById(id: Int)(implicit s: DBSession = auto): Option[Reservation] = {    
    withSQL {
      select.from(Reservation as r).where.eq(r.id, id)
    }.map(Reservation(r.resultName)).single.apply()
  }
    
 def findAll()(implicit s: DBSession = auto): Seq[Reservation] = {    
   null
 }
  
  def create(reservation: Reservation) {
   
  }
  
}
