package services

import models.{Notes, User}

case class UserDTO(id: Int, name: String, clanId: String, status: String, remoteAddress: String,
  penalties: Seq[Notes]) 

object UserDTO{
  
  def getAllUsersDTO(notes: Seq[Notes], users: Seq[User]): Seq[UserDTO] = {      
    users.map(u => getNotesForUser(u, notes))
  }
  
  def getNotesForUser(user: User, notes: Seq[Notes]): UserDTO = {
    new UserDTO(user.id, user.name, user.clanId, user.status, user.remoteAddress, notes.filter(p => p.name.contains(user.name)))    
  }
  
}
