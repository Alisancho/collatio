package ru.finance.analyst


import akka.actor.ActorSystem
import akka.http.scaladsl.{ConnectionContext, Http}
import akka.http.scaladsl.model.Uri
import akka.stream.Materializer
import monix.execution.Scheduler
import ru.finance.analyst.boot.{ContextBoot, YahooBoot}
import ru.finance.analyst.config.Config.{ServerConfig, TelegramConfig}
import ru.finance.analyst.controler.http.HttpController
import ru.finance.analyst.service.InvestInfoBot

import scala.concurrent.Future
import java.util.logging.Logger
import javax.net.ssl.SSLContext

object AppStart extends App, ContextBoot, YahooBoot {

  val controller = new HttpController(yahooFinanceService)
  
  Http()(system)
    .newServerAt(ServerConfig.host, ServerConfig.port)
    .bindFlow(controller.getRout)

//  val nj = new InvestInfoBot(TelegramConfig.token,TelegramConfig.name,TelegramConfig.mainChatID)

  
}
