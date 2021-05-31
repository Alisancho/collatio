package ru.finance.analyst

import com.typesafe.scalalogging.LazyLogging
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import ru.finance.analyst.boot.{ContextBoot, ElasticsearchBoot}
import ru.finance.analyst.config.Config
import ru.finance.analyst.config.Config.{ServerConfig, TelegramConfig}
import ru.finance.analyst.service.YahooFinanceServiceFutureImpl

import ru.finance.analyst.controler.telegram.{TelegramController, TelegramControllerConfig}

import ru.finance.analyst.ropository.MonitoringTaskRep
import ru.finance.analyst.service.BuisnessTaskServiceImpl
import ru.finance.analyst.telegram.keyboard.KeyBoards.MAIN_KEY_BOARD

object AppStart extends App with ContextBoot with ElasticsearchBoot with LazyLogging {
  ApiContextInitializer.init()
  import com.softwaremill.macwire._

  val telegramServiceConfig: TelegramControllerConfig =
    TelegramControllerConfig(
      TelegramConfig.token,
      TelegramConfig.name,
      TelegramConfig.mainChatID
    )

  val theMonitoringTaskRep: MonitoringTaskRep = wire[MonitoringTaskRep]
  val theYahooFinanceServiceImpl: YahooFinanceServiceFutureImpl = wire[YahooFinanceServiceFutureImpl]
  val theBuisnessTaskServiceImpl: BuisnessTaskServiceImpl = wire[BuisnessTaskServiceImpl]
  val theTelegramController: TelegramController = wire[TelegramController]

  val telegramBotsApi = new TelegramBotsApi()
  telegramBotsApi.registerBot(theTelegramController)

  logger.info("START_SERVER")

//  val controller: HttpController = new HttpController(theYahooFinanceServiceImpl)
//
//  Http()
//    .newServerAt(ServerConfig.host, ServerConfig.port)
//    .bindFlow(controller.getRout)

  theTelegramController.sendMessage("START_SERVER", Config.TelegramConfig.mainChatID, MAIN_KEY_BOARD)

}
