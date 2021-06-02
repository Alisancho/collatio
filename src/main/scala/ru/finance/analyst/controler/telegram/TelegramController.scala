package ru.finance.analyst.controler.telegram

import akka.stream.Materializer
import com.telega.TelegramService
import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.finance.analyst.controler.telegram.TelegramController.regexDell
import ru.finance.analyst.service.{BuisnessTaskServiceImpl, YahooFinanceServiceFutureImpl}
import ru.finance.analyst.telegram.keyboard.KeyBoards.MAIN_KEY_BOARD
import ru.finance.analyst.typeclass.algebra.MessengerTypeObject.{MessengerTypeMonitoringTask, MessengerTypeYahooStock}
import ru.finance.analyst.entity.elastic.JsonHelper._
import ru.finance.analyst.telegram.keyboard.KeyBoards.MonitoringTask.BUTTON_DELL_TASK

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}
import scala.util.matching.Regex

case class TelegramControllerConfig(token: String, name: String, chat_id: Long)

object TelegramController                      {
//  val regexDell: Regex = "^DELL:[\\w\\S]*".r
  val regexDell: Regex = "^DELETE:".r
}

class TelegramController(conf: TelegramControllerConfig)(
    yahooFinanceService: YahooFinanceServiceFutureImpl,
    buisnessTaskService: BuisnessTaskServiceImpl
)(implicit
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
              .foreach(
                _.foreach(l =>
                  this.sendMessage(l.getTelegramMessenger, update.getMessage.getChatId, BUTTON_DELL_TASK(l.taskId))
                )
              )
          }
          case _               => this.sendMessage("ERROR", update.getMessage.getChatId, MAIN_KEY_BOARD)

        }
      }
    } else if (update.hasCallbackQuery && update.getCallbackQuery.getData != "") {
      val mess:String    = update.getCallbackQuery.getData
      val o = regexDell.pattern.matcher(mess).replaceFirst("")
      val task = update.getCallbackQuery.getData
      val chatId = update.getCallbackQuery.getMessage.getChatId
      buisnessTaskService.deleteMyTask(chatId,task).onComplete{
        case Success(value) => this.sendMessage("OK", update.getCallbackQuery.getMessage.getChatId)
        case Failure(exception) => this.sendMessage(exception.getMessage, update.getCallbackQuery.getMessage.getChatId)
      }
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
