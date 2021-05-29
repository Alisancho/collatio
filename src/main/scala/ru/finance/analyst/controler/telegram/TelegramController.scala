package ru.finance.analyst.controler.telegram

import akka.stream.Materializer
import com.telega.TelegramService
import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.meta.api.objects.Update
import ru.finance.analyst.config.Config
import ru.finance.analyst.entity.yahoo.JsonSupportYahoo
import ru.finance.analyst.entity.yahoo.summary.YahooSummaryResponse
import ru.finance.analyst.service.YahooFinanceService.SUMMARY
import ru.finance.analyst.service.{BuisnessTaskServiceImpl, YahooFinanceServiceImpl}
import spray.json.JsonParser

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt

case class TelegramControllerConfig(token: String, name: String, chat_id: Long)


class TelegramController(conf: TelegramControllerConfig)(
    buisnessTaskServiceImpl: BuisnessTaskServiceImpl,
    yahooFinanceServiceImpl: YahooFinanceServiceImpl
)(implicit ex: ExecutionContextExecutor, materializer: Materializer)
    extends TelegramService() with JsonSupportYahoo with LazyLogging {

  logger.info("START_TelegramController")

  override def onUpdateReceived(update: Update): Unit = {
      logger.info("NEW_MESSENGER")
      if (update.getMessage != null && update.getMessage.hasText) {
        val text = update.getMessage.getText
        text match {
          case "Узнать S&P500" => searchIndexInYahoo("^GSPC","S&P500 = ",update)
          case "Узнать DJI"    => searchIndexInYahoo("^DJI","DJI = ",update)
          case "Узнать VIX"    => searchIndexInYahoo("^VIX","VIX = ",update)
          case _ => this.sendMessage("ERROR", update.getMessage.getChatId)
        }
      }
    }

  override def getBotUsername: String = conf.name

  override def getBotToken: String = conf.token

  val searchIndexInYahoo:(String, String, Update) => Unit = (tiker,mess,update) =>
    yahooFinanceServiceImpl
      .getInfo(tiker, "US", SUMMARY)
      .flatMap(_.entity.toStrict(3.second))
      .map(_.data.utf8String)
      .map(g => JsonParser(g).convertTo[YahooSummaryResponse])
      .map(_.price.regularMarketPrice.raw)
      .foreach(l => this.sendMessage(mess + l, update.getMessage.getChatId))

}
