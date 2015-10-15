package models

case class Account(id: Int, email: String, password: String, name: String, role: Role)

object Account {

  def authenticate(email: String, password: String): Option[Account] = {
    findByEmail(email)
  }

  def findByEmail(email: String): Option[Account] = {
    null
  }

  def findById(id: Int): Option[Account] = {
    null
  }

  def findAll(): Seq[Account] = { 
    null
  }

  def create(account: Account) {
   
  }
  
}
