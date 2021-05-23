package ru.finance.analyst.boot

import akka.actor.ActorSystem
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor

trait ContextBoot {

  val system: ActorSystem = ActorSystem()

  implicit val materializer: Materializer= Materializer(system)

  implicit val ex: ExecutionContextExecutor = system.dispatcher

}
