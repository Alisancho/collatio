package ru.finance.analyst.typeclass

trait MessengerType[G] {
  def getTelegramMessenger:String
}

