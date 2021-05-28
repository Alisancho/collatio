package ru.finance.analyst.ropository

import akka.NotUsed
import akka.stream.alpakka.elasticsearch.WriteResult
import akka.stream.scaladsl.Source
import ru.finance.analyst.entity.elastic.MonitoringTask
import spray.json.{JsonReader, JsonWriter}

trait ElasticRep[T] {
  def createIndexMessage(id: String, ko: T)(implicit f: JsonWriter[T]): Source[WriteResult[T, NotUsed], NotUsed]
  def selectObjects(implicit k: JsonReader[MonitoringTask]): Source[MonitoringTask, NotUsed]
}
