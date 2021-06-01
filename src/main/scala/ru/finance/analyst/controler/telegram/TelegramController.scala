package ru.finance.analyst.controler.telegram

import akka.stream.Materializer
import com.telega.TelegramService
import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.finance.analyst.service.{BuisnessTaskServiceImpl, YahooFinanceServiceFutureImpl}
import ru.finance.analyst.telegram.keyboard.KeyBoards.MAIN_KEY_BOARD
import ru.finance.analyst.typeclass.algebra.MessengerTypeObject.{MessengerTypeMonitoringTask, MessengerTypeYahooStock}
import ru.finance.analyst.entity.elastic.JsonHelper._
import ru.finance.analyst.telegram.keyboard.KeyBoards.MonitoringTask.BUTTON_DELL_TASK

import scala.concurrent.{ExecutionContextExecutor, Future}


case class TelegramControllerConfig(token: String, name: String, chat_id: Long)

class TelegramController(conf: TelegramControllerConfig)(yahooFinanceService: YahooFinanceServiceFutureImpl,
                                                         buisnessTaskService:BuisnessTaskServiceImpl)(implicit
    ex: ExecutionContextExecutor,
    materializer: Materializer
) extends TelegramService() with LazyLogging {

  logger.info("START_TelegramController")

  override def onUpdateReceived(update: Update): Unit = {
    if (update.hasMessage) {
      if (update.getMessage.hasText) {
        val text = update.getMessage.getText
        text match {
          case "Узнать S&P500" => searchTicker("^GSPC", update)
          case "Узнать DJI"    => searchTicker("^DJI", update)
          case "Узнать VIX"    => searchTicker("^VIX", update)
          case "CREATE_TASK"   => this.sendMessage("ERROR", update.getMessage.getChatId, MAIN_KEY_BOARD)
          case "DELL_TASK"     => this.sendMessage("ERROR", update.getMessage.getChatId, MAIN_KEY_BOARD)
          case "MY_TASKS"      => {
            buisnessTaskService
              .getMyActiveTask(update.getMessage.getChatId)
              .foreach(_.foreach(l => this.sendMessage(l.getTelegramMessenger, update.getMessage.getChatId, BUTTON_DELL_TASK)))
          }
          case _               => this.sendMessage("ERROR", update.getMessage.getChatId, MAIN_KEY_BOARD)

        }
      }
    } else if (update.hasCallbackQuery) {
      logger.info(update.getCallbackQuery.toString)
    }

  }

  override def getBotUsername: String                 = conf.name

  override def getBotToken: String = conf.token

  val searchTicker: (String, Update) => Future[Unit] = (ticker, update) =>
    for {
      res <- yahooFinanceService.getStock(ticker)
      _    = this.sendMessage(res.getTelegramMessenger, update.getMessage.getChatId, MAIN_KEY_BOARD)
    } yield ()

}
