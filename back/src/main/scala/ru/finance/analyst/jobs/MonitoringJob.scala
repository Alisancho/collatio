package ru.finance.analyst.jobs

import akka.stream.Materializer
import akka.stream.scaladsl.Source
import ru.finance.analyst.entity.elastic.MonitoringTask
import ru.finance.analyst.ropository.ElasticsearchRep
import ru.finance.analyst.service.TelegramServiceImpl
import spray.json.{JsonFormat, JsonReader, JsonWriter}

import scala.concurrent.duration._

object MonitoringJob {
  def startMonitoringJob(elasticsearchRep: ElasticsearchRep,
                         telegramServiceImpl: TelegramServiceImpl)
                        (using h: JsonReader[MonitoringTask], materializer: Materializer) ={
    Source.tick(4.second,10.second,"")
      .flatMapConcat(k => 
        elasticsearchRep.getMonitoringTask
      ).map(z => println(z.toString))
      .runForeach(i => ())
  }
}
