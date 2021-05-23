package ru.finance.analyst.service

import com.telega.TelegramService
import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.meta.api.objects.Update

case class TelegramServiceConfig(token: String, name: String, chat_id: Long)

class TelegramServiceImpl(conf: TelegramServiceConfig)
                         (buisnessTaskServiceImpl: BuisnessTaskServiceImpl)
    extends TelegramService with LazyLogging {
  logger.info("START_TelegramServiceImpl")

  override def onUpdateReceived(update: Update): Unit = {
    if (update.getMessage != null && update.getMessage.hasText && update.getMessage.getChatId == conf.chat_id) {}
  }

  override def getBotUsername: String = conf.name

  override def getBotToken: String = conf.token

}
