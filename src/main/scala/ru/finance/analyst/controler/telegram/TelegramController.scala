package ru.finance.analyst.controler.telegram

import akka.stream.Materializer
import com.telega.TelegramService
import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.meta.api.objects.Update
import ru.finance.analyst.entity.yahoo.JsonSupportYahoo
import ru.finance.analyst.entity.yahoo.summary.YahooSummaryResponse
import ru.finance.analyst.service.YahooFinanceService.SUMMARY
import ru.finance.analyst.service.YahooFinanceServiceImpl
import ru.finance.analyst.telegram.keyboard.KeyBoards.MAIN_KEY_BOARD
import spray.json.JsonParser

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt

case class TelegramControllerConfig(token: String, name: String, chat_id: Long)

class TelegramController(conf: TelegramControllerConfig)(yahooFinanceServiceImpl: YahooFinanceServiceImpl)(implicit
    ex: ExecutionContextExecutor,
    materializer: Materializer
) extends TelegramService() with JsonSupportYahoo with LazyLogging {
  import ru.finance.analyst.typeclass.algebra.MessengerTypeObject._
  logger.info("START_TelegramController")

  override def onUpdateReceived(update: Update): Unit = {
    logger.info("NEW_MESSENGER")
    if (update.getMessage != null && update.getMessage.hasText) {
      val text = update.getMessage.getText
      text match {
        case "Узнать S&P500" => searchIndexInYahoo("^GSPC", update)
        case "Узнать DJI"    => searchIndexInYahoo("^DJI", update)
        case "Узнать VIX"    => searchIndexInYahoo("^VIX", update)
        case _               => this.sendMessage("ERROR", update.getMessage.getChatId, MAIN_KEY_BOARD)
      }
    }
  }

  override def getBotUsername: String                 = conf.name

  override def getBotToken: String = conf.token

  val searchIndexInYahoo: (String, Update) => Unit = (tiker, update) =>
    yahooFinanceServiceImpl
      .getInfo(tiker, "US", SUMMARY)
      .flatMap(_.entity.toStrict(3.second))
      .map(_.data.utf8String)
      .map(g => JsonParser(g).convertTo[YahooSummaryResponse])
      .foreach(l => this.sendMessage(l.getTelegramMessenger, update.getMessage.getChatId, MAIN_KEY_BOARD))

}
