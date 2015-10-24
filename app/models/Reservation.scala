package models

import scalikejdbc._

case class Reservation(id: Int, name: String, number: String, status: String)

object Reservation extends SQLSyntaxSupport[Reservation]  {
  
  private val auto = AutoSession
  
  def findById(id: Int)(implicit s: DBSession = auto): Option[Reservation] = {
     null
  }
  
 def findAll()(implicit s: DBSession = auto): Seq[Reservation] = {    
   null
 }
  
  def create(account: Reservation) {
   
  }
  
}
