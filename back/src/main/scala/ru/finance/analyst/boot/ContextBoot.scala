package ru.finance.analyst.boot

import akka.actor.ActorSystem
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor

trait ContextBoot:

  val system: ActorSystem = ActorSystem()

  given materializer: Materializer = Materializer(system)

  given ex:ExecutionContextExecutor = system.dispatcher

  
