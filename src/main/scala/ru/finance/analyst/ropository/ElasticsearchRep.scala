package ru.finance.analyst.ropository

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.alpakka.elasticsearch.{ElasticsearchParams, ElasticsearchSourceSettings, ElasticsearchWriteSettings, ReadResult, WriteMessage, WriteResult}
import akka.stream.alpakka.elasticsearch.scaladsl.{ElasticsearchFlow, ElasticsearchSource}
import akka.stream.scaladsl.{Sink, Source}
import com.fasterxml.jackson.annotation.JsonFormat
import ru.finance.analyst.entity.elastic.MonitoringTask
import spray.json.{JsonFormat, JsonReader, JsonWriter}

import scala.concurrent.Future

class ElasticsearchRep(elasticsearchParams:ElasticsearchParams, 
                       elasticsearchWriteSettings:ElasticsearchWriteSettings,
                       sourceSettings:ElasticsearchSourceSettings)
                      (implicit materializer:Materializer) {

  def createIndexMessage[T](id: String, ko: T)(implicit f: JsonWriter[T]): Future[Seq[WriteResult[T, NotUsed]]] = {
    Source.single(ko)
      .map(m => WriteMessage.createIndexMessage(id = id, source = m))
      .via(
        ElasticsearchFlow.create[T](elasticsearchParams, elasticsearchWriteSettings)
      )
      .runWith(Sink.seq)
  }

  def getMonitoringTask(implicit k: JsonReader[MonitoringTask]): Source[MonitoringTask, NotUsed] =
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
