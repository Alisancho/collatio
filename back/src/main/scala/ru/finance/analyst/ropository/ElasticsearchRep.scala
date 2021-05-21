package ru.finance.analyst.ropository

import akka.stream.Materializer
import akka.stream.alpakka.elasticsearch.{ElasticsearchParams, ElasticsearchWriteSettings, WriteMessage}
import akka.stream.alpakka.elasticsearch.scaladsl.ElasticsearchFlow
import akka.stream.scaladsl.{Sink, Source}
import spray.json.{JsonFormat, JsonWriter}

class ElasticsearchRep(elasticsearchParams:ElasticsearchParams, 
                       elasticsearchWriteSettings:ElasticsearchWriteSettings)
                      (using materializer:Materializer) {
  def createIndexMessage[T](id:String, ko:T)(using JsonWriter[T] | JsonFormat[T] ) = {
    Source.single(ko)
      .map(m => WriteMessage.createIndexMessage(id = id, source = m))
      .via(
        ElasticsearchFlow.create[T](elasticsearchParams, elasticsearchWriteSettings)
      )
      .runWith(Sink.seq)
  }
}
