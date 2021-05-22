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

//  val replyKeyboardMarkup = new ReplyKeyboardMarkup()
//    .setSelective(true)
//    .setResizeKeyboard(true)
//    .setOneTimeKeyboard(false)
  
//    val keyboardFirstRow1 = new KeyboardRow
//    val keyboardFirstRow2 = new KeyboardRow
//    val keyboardFirstRow3 = new KeyboardRow
//  
//    keyboardFirstRow3.add(new KeyboardButton(ConfigObject.UPDATE_TOOLS))
//    keyboardFirstRow2.add(new KeyboardButton(ConfigObject.ANALYTICS_STOP))
//    keyboardFirstRow1.add(new KeyboardButton(ConfigObject.ANALYTICS_START))
//  
//    replyKeyboardMarkup.setKeyboard((keyboardFirstRow1 :: keyboardFirstRow2 :: keyboardFirstRow3 :: Nil))


  override def onUpdateReceived(update: Update): Unit = {
    if (update.getMessage != null && update.getMessage.hasText && update.getMessage.getChatId == chat_id) {

    }

  }

  override def getBotUsername: String = name

  override def getBotToken: String = token

  def sendMessage(mess: String, chatId:Long  = chat_id): Unit = {
    val sendMessage = new SendMessage(chatId, mess)
//    sendMessage.setReplyMarkup(replyKeyboardMarkup)
    try execute(sendMessage)
    catch {
      case ignored: Throwable => 
    }
  }
}
