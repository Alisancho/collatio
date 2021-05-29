package ru.finance.analyst.ropository

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.alpakka.elasticsearch.{
  ElasticsearchParams,
  ElasticsearchSourceSettings,
  ElasticsearchWriteSettings,
  WriteMessage,
  WriteResult
}
import akka.stream.alpakka.elasticsearch.scaladsl.{ElasticsearchFlow, ElasticsearchSource}
import akka.stream.scaladsl.Source
import com.typesafe.scalalogging.LazyLogging
import ru.finance.analyst.entity.elastic.MonitoringTask
import spray.json.{JsonReader, JsonWriter}

object MonitoringTaskRep {

  val ALL_ACTIVE_TASK:String =
    """{
      |     "match": {
      |       "status" : "ACTIVE"
      |     }
      | }""".stripMargin

  val ALL_ACTIVE_TASK_CHAT_ID:Long => String = chatId =>
    s"""{
      |     "match": {
      |       "chatId" : $chatId
      |     }
      | }""".stripMargin
}

class MonitoringTaskRep(
    elasticsearchParams: ElasticsearchParams,
    elasticsearchWriteSettings: ElasticsearchWriteSettings,
    sourceSettings: ElasticsearchSourceSettings
)(implicit materializer: Materializer)
    extends LazyLogging  {

  logger.info("START_ElasticsearchRep")

  def createIndexMessage[T](id: String, ko: T)
                           (implicit f: JsonWriter[T]): Source[WriteResult[T, NotUsed], NotUsed] = {
    Source
      .single(ko)
      .map(m => WriteMessage.createIndexMessage(id = id, source = m))
      .via(
        ElasticsearchFlow.create[T](elasticsearchParams, elasticsearchWriteSettings)
      )
  }

  def getOnlyActiveTask(query:String)
                       (implicit k: JsonReader[MonitoringTask]): Source[MonitoringTask, NotUsed] =
    ElasticsearchSource
      .typed[MonitoringTask](
        elasticsearchParams,
        query,
        sourceSettings
      )
      .map(_.source)

}
