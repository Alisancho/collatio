package ru.finance.analyst


import akka.actor.ActorSystem
import akka.http.scaladsl.{ConnectionContext, Http}
import akka.http.scaladsl.model.Uri
import akka.stream.Materializer
import akka.stream.alpakka.elasticsearch.{ApiVersion, ElasticsearchConnectionSettings, ElasticsearchParams, ElasticsearchWriteSettings, RetryAtFixedRate, WriteMessage}
import akka.stream.alpakka.elasticsearch.scaladsl.ElasticsearchFlow
import akka.stream.scaladsl.{Sink, Source}
import monix.execution.Scheduler
import ru.finance.analyst.boot.{ContextBoot, YahooBoot}
import ru.finance.analyst.config.Config.{ServerConfig, TelegramConfig}
import ru.finance.analyst.controler.http.HttpController
import ru.finance.analyst.ropository.ElasticsearchRep
import ru.finance.analyst.service.InvestInfoBot

import java.util.Date
import scala.concurrent.duration._
import scala.concurrent.Future
import javax.net.ssl.SSLContext

case class JobTask(taskId:String,
                   chatId:Long,
                   status:String,
                   yahooSymbol:String,
                   yahooRegion:String,
                   priceLimit:Float,
                   priceUp:Boolean
                  )
case class Kooooo()

object AppStart extends App, ContextBoot, YahooBoot {
  import spray.json._
  import DefaultJsonProtocol._

  given JsonFormat[JobTask] = jsonFormat7(JobTask.apply)

  val controller = new HttpController(yahooFinanceService)
  
  Http()(system)
    .newServerAt(ServerConfig.host, ServerConfig.port)
    .bindFlow(controller.getRout)
  


//  val nj = new InvestInfoBot(TelegramConfig.token,TelegramConfig.name,TelegramConfig.mainChatID)


}
