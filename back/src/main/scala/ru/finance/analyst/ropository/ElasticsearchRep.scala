package ru.finance.analyst.ropository

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.alpakka.elasticsearch.{ElasticsearchParams, ElasticsearchSourceSettings, ElasticsearchWriteSettings, ReadResult, WriteMessage}
import akka.stream.alpakka.elasticsearch.scaladsl.{ElasticsearchFlow, ElasticsearchSource}
import akka.stream.scaladsl.{Sink, Source}
import ru.finance.analyst.entity.elastic.MonitoringTask
import spray.json.{JsonFormat, JsonReader, JsonWriter}

class ElasticsearchRep(elasticsearchParams:ElasticsearchParams, 
                       elasticsearchWriteSettings:ElasticsearchWriteSettings,
                       sourceSettings:ElasticsearchSourceSettings)
                      (using materializer:Materializer) {

  def createIndexMessage[T](id:String, ko:T)(using JsonWriter[T] | JsonFormat[T] ) = {
    Source.single(ko)
      .map(m => WriteMessage.createIndexMessage(id = id, source = m))
      .via(
        ElasticsearchFlow.create[T](elasticsearchParams, elasticsearchWriteSettings)
      )
      .runWith(Sink.seq)
  }

  def getMonitoringTask(using JsonReader[MonitoringTask]) =
    ElasticsearchSource
      .typed[MonitoringTask](
        elasticsearchParams,
        searchParams = Map(
          "query" -> """ {"match_all": {}} """,
          "_source" -> """ ["id", "a", "c"] """
        ),
        sourceSettings
      ).map(_.source)
}
