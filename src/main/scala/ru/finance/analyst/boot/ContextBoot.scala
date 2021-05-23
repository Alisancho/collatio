package ru.finance.analyst.boot

import akka.actor.ActorSystem
import akka.stream.Materializer
import ru.finance.analyst.config.Config

import scala.concurrent.ExecutionContextExecutor

trait ContextBoot {

  val system: ActorSystem = ActorSystem("mainActorSystem", Config.config)

  implicit val materializer: Materializer = Materializer(system)

  implicit val ex: ExecutionContextExecutor = system.dispatcher

}
