package ru.finance.analyst.boot

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import ru.finance.analyst.service.{YahooFinanceConfig, YahooFinanceServiceImpl}
import ru.finance.analyst.config.Config.YahooConfig

trait YahooBoot {
  val system: ActorSystem

  val yahooFinanceConfig: YahooFinanceConfig = YahooFinanceConfig(
    YahooConfig.uriHost,
    YahooConfig.xRapidapiHost,
    YahooConfig.token
  )
  val yahooFinanceService = new YahooFinanceServiceImpl(yahooFinanceConfig)(r => Http()(system).singleRequest(r))
}
