package ru.finance.analyst

import akka.http.scaladsl.Http

import ru.finance.analyst.boot.{ContextBoot, ElasticsearchBoot, YahooBoot}
import ru.finance.analyst.config.Config.{ServerConfig, TelegramConfig}
import ru.finance.analyst.controler.http.HttpController
import ru.finance.analyst.service.{BuisnessTaskServiceImpl, TelegramServiceImpl}


object AppStart extends App with ContextBoot with ElasticsearchBoot with YahooBoot {

  val buisnessTaskServiceImpl = new BuisnessTaskServiceImpl(yahooFinanceService, elasticsearchRep)

  val telegramServiceImpl = new TelegramServiceImpl(TelegramConfig.token, TelegramConfig.name, TelegramConfig.mainChatID)(
    buisnessTaskServiceImpl
  )

  val controller = new HttpController(yahooFinanceService)

  Http()(system)
    .newServerAt(ServerConfig.host, ServerConfig.port)
    .bindFlow(controller.getRout())
}
