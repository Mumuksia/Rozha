package models

sealed trait Role

object Role {
  
  case object Administrator extends Role
  case object NormalUser extends Role
  case object KVHost extends Role

  def valueOf(value: String): Role = value match {
    case "Administrator" => Administrator
    case "NormalUser"    => NormalUser
    case "KVHost"  => KVHost
    case _ => throw new IllegalArgumentException()
  }
}

