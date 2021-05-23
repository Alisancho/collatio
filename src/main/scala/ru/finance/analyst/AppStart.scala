package ru.finance.analyst

import akka.http.scaladsl.Http
import com.typesafe.scalalogging.LazyLogging
import ru.finance.analyst.boot.{ContextBoot, ElasticsearchBoot}
import ru.finance.analyst.config.Config.{ServerConfig, TelegramConfig, YahooConfig}
import ru.finance.analyst.controler.http.HttpController
import ru.finance.analyst.jobs.MonitoringJob
import ru.finance.analyst.ropository.ElasticsearchRep
import ru.finance.analyst.service.{
  BuisnessTaskServiceImpl,
  TelegramServiceConfig,
  TelegramServiceImpl,
  YahooFinanceConfig,
  YahooFinanceServiceImpl
}

import scala.util.{Failure, Success}

object AppStart extends App with ContextBoot with ElasticsearchBoot with LazyLogging {
  import com.softwaremill.macwire._

  val yahooFinanceConfig: YahooFinanceConfig = YahooFinanceConfig(
    YahooConfig.uriHost,
    YahooConfig.xRapidapiHost,
    YahooConfig.token,
    r => Http()(system).singleRequest(r)
  )

  val telegramServiceConfig: TelegramServiceConfig =
    TelegramServiceConfig(
      TelegramConfig.token,
      TelegramConfig.name,
      TelegramConfig.mainChatID
    )

  val theElasticsearchRep        = wire[ElasticsearchRep]
  val theYahooFinanceServiceImpl = wire[YahooFinanceServiceImpl]
  val theBuisnessTaskServiceImpl = wire[BuisnessTaskServiceImpl]
  val theTelegramServiceImpl     = wire[TelegramServiceImpl]

  MonitoringJob
    .startMonitoringJob(theElasticsearchRep, theYahooFinanceServiceImpl)
    .runForeach(_ => ())
    .onComplete({
      case Success(value)     => logger.info("STOP_JOB")
      case Failure(exception) => logger.error(exception.getLocalizedMessage)
    })
  logger.info("START_SERVER")

  val controller: HttpController = new HttpController(theYahooFinanceServiceImpl)
//
  Http()(system)
    .newServerAt(ServerConfig.host, ServerConfig.port)
    .bindFlow(controller.getRout())

//  val id      = UUID.randomUUID().toString
//  val task_id = "8-" + UUID.randomUUID().toString.replaceAll("-", "").substring(23)
//  println(task_id)
//  val jjj     = MonitoringTask(task_id, TelegramConfig.mainChatID, "ACTIVE", "^GSPC", "US", 3800, priceUp = false)

//  MonitoringJob.startMonitoringJob(theElasticsearchRep, theYahooFinanceServiceImpl).runForeach(_ => ())

}
