package models

import play.api.db._
import play.api.Play._
import anorm._
import anorm.SqlParser._

case class User(id: Int, name: String, clanId: String, status: String, remoteAddress: String)

object User {
  
    val accountParser: RowParser[User] = (
    SqlParser.int("id") ~
    SqlParser.str("name") ~
    SqlParser.str("clanId") ~
    SqlParser.str("status") ~
    SqlParser.str("remoteAddress")
  ) map {
      case id ~ name ~ clanId ~ status ~ remoteAddress=>
        User(id, name, clanId, status, remoteAddress)
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
      SQL("select * from public.User").
      as(allRowsListParser)
    }
  }
  
  def create(account: User) {
   
  }
  
  def findAllByStatus(status: String): Seq[User] = {
      DB.withConnection { implicit c =>
      SQL("select * from public.User where status = {status}").
      on('status -> status).
      as(allRowsListParser)
    }
  }
  
  def create(clanId: String, name: String, status: String, remoteAddress: String){
    DB.withConnection { implicit c =>
      SQL("insert into public.User(name, status, clanId, remoteAddress) values ({name}, {status}, {clanId}, {remoteAddress})").
        on( 'name -> name, 'status -> status, 'clanId -> clanId,
        'remoteAddress -> remoteAddress).
        executeInsert()
    }
  }
  
  def createLight(clanId: String, name: String){
    DB.withConnection { implicit c =>
      SQL("insert into public.User(name, status, clanId, remoteAddress) values ({name}, 'some', {clanId}, 'some')").
        on( 'name -> name,  'clanId -> clanId).
        executeInsert()
    }
  }
  
  def createLightUsers(users: Seq[User]){
      clearAllUsers
      users.foreach(u=>if (u != null) {createLight(u.clanId, u.name)})
  }
  
  def clearAllUsers() {
    DB.withConnection { implicit c =>
      SQL("delete from public.User")
        .executeUpdate()
    }
  }
  
}

