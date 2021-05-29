package ru.finance.analyst

import akka.http.scaladsl.Http
import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import ru.finance.analyst.boot.{ContextBoot, ElasticsearchBoot}
import ru.finance.analyst.config.Config
import ru.finance.analyst.config.Config.{ServerConfig, TelegramConfig, YahooConfig}
import ru.finance.analyst.controler.http.HttpController
import ru.finance.analyst.controler.telegram.{TelegramController, TelegramControllerConfig}
import ru.finance.analyst.entity.yahoo.JsonSupportYahoo
import ru.finance.analyst.ropository.MonitoringTaskRep.ALL_ACTIVE_TASK_CHAT_ID
import ru.finance.analyst.ropository.{MonitoringTaskRep, YahooFinanceRep}
import ru.finance.analyst.service.{BuisnessTaskServiceImpl, YahooFinanceConfig, YahooFinanceServiceImpl}

object AppStart extends App
  with ContextBoot
  with ElasticsearchBoot
  with LazyLogging
  with JsonSupportYahoo {
  ApiContextInitializer.init()
  import com.softwaremill.macwire._

  val yahooFinanceConfig: YahooFinanceConfig = YahooFinanceConfig(
    YahooConfig.uriHost,
    YahooConfig.xRapidapiHost,
    YahooConfig.token,
    r => Http()(system).singleRequest(r)
  )

  val telegramServiceConfig: TelegramControllerConfig =
    TelegramControllerConfig(
      TelegramConfig.token,
      TelegramConfig.name,
      TelegramConfig.mainChatID
    )

  val theMonitoringTaskRep       = wire[MonitoringTaskRep]
  val theYahooFinanceRep         = wire[YahooFinanceRep]
  val theYahooFinanceServiceImpl = wire[YahooFinanceServiceImpl]
  val theBuisnessTaskServiceImpl = wire[BuisnessTaskServiceImpl]
  val theTelegramController      = wire[TelegramController]


  val telegramBotsApi =  new TelegramBotsApi()
  telegramBotsApi.registerBot(theTelegramController)

//  MonitoringJob
//    .startMonitoringJob(theMonitoringTaskRep, theYahooFinanceServiceImpl)
//    .runForeach(_ => ())
//    .onComplete({
//      case Success(value)     => logger.info("STOP_JOB")
//      case Failure(exception) => logger.error(exception.getLocalizedMessage)
//    })
  logger.info("START_SERVER")

  val controller: HttpController = new HttpController(theYahooFinanceServiceImpl)

  Http()
    .newServerAt(ServerConfig.host, ServerConfig.port)
    .bindFlow(controller.getRout)

  theTelegramController.sendMessage("START_SERVER",Config.TelegramConfig.mainChatID)


  theMonitoringTaskRep
    .getOnlyActiveTask(ALL_ACTIVE_TASK_CHAT_ID(61226443))
    .runForeach(m => logger.info(m.toString))
    .failed
    .foreach(error => logger.error(error.getMessage))


}
