package ru.finance.analyst


import akka.actor.ActorSystem
import akka.http.scaladsl.{ConnectionContext, Http}
import akka.http.scaladsl.model.Uri
import akka.stream.Materializer
import akka.stream.alpakka.elasticsearch.{ApiVersion, ElasticsearchConnectionSettings, ElasticsearchParams, ElasticsearchWriteSettings, RetryAtFixedRate, WriteMessage}
import akka.stream.alpakka.elasticsearch.scaladsl.ElasticsearchFlow
import akka.stream.scaladsl.{Sink, Source}
import ru.finance.analyst.boot.{ContextBoot, ElasticsearchBoot, YahooBoot}
import ru.finance.analyst.config.Config.{ServerConfig, TelegramConfig}
import ru.finance.analyst.controler.http.HttpController
import ru.finance.analyst.ropository.ElasticsearchRep
import ru.finance.analyst.service.{BuisnessTaskServiceImpl, TelegramServiceImpl}

import scala.concurrent.duration._
import scala.concurrent.Future
import javax.net.ssl.SSLContext

object AppStart extends App,  ContextBoot, ElasticsearchBoot, YahooBoot {

  val buisnessTaskServiceImpl = new BuisnessTaskServiceImpl(yahooFinanceService, elasticsearchRep)

  val telegramServiceImpl = new TelegramServiceImpl(TelegramConfig.token,TelegramConfig.name,TelegramConfig.mainChatID)(buisnessTaskServiceImpl)

  val controller = new HttpController(yahooFinanceService)

  Http()(system)
    .newServerAt(ServerConfig.host, ServerConfig.port)
    .bindFlow(controller.getRout())
}
