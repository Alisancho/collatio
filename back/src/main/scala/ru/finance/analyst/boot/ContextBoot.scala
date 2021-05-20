package ru.finance.analyst.boot

import akka.actor.ActorSystem
import akka.stream.Materializer
import monix.execution.Scheduler
import monix.execution.schedulers.SchedulerService

trait ContextBoot:
  given scheduler:SchedulerService = Scheduler.fixedPool("MainPool", 4)

  val system: ActorSystem = ActorSystem()

  given materializer: Materializer = Materializer(system)

  
