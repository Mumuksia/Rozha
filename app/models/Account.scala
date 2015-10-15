package models

case class Account(id: Int, email: String, password: String, name: String, role: Role)

object Account {

  def authenticate(email: String, password: String): Option[Account] = {
    findByEmail(email)
  }

  def findByEmail(email: String): Option[Account] = {
    Option.apply(new Account(1, "test@gmail.com", "test", "test", Role.NormalUser))
  }

  def findById(id: Int): Option[Account] = {
    Option.apply(new Account(1, "test@gmail.com", "test", "test", Role.NormalUser))
  }

  def findAll(): Seq[Account] = { 
    null
  }

  def create(account: Account) {
   
  }
  
}
