package ru.finance.analyst.service

import com.typesafe.config.ConfigObject
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

import java.util.Objects

class TelegramServiceImpl(token: String, 
                          name: String, 
                          chat_id: Long)(buisnessTaskServiceImpl:BuisnessTaskServiceImpl) extends TelegramLongPollingBot {

  override def onUpdateReceived(update: Update): Unit = {
    if (update.getMessage != null && update.getMessage.hasText && update.getMessage.getChatId == chat_id) {
    }
  }

  override def getBotUsername: String = name

  override def getBotToken: String = token

//  def sendMessage(mess: String, chatId:Long  = chat_id): Unit = {
//    val sendMessage = new SendMessage(chatId, mess)
////    sendMessage.setReplyMarkup(replyKeyboardMarkup)
//    try {
//      execute(sendMessage)
//    } catch {
//      case ignored: Throwable =>
//    }
//  }
}
