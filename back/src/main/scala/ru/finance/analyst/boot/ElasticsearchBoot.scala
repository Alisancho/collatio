package ru.finance.analyst.boot

import akka.stream.Materializer
import akka.stream.alpakka.elasticsearch.{ApiVersion, ElasticsearchConnectionSettings, ElasticsearchParams, ElasticsearchSourceSettings, ElasticsearchWriteSettings, RetryAtFixedRate}
import ru.finance.analyst.ropository.ElasticsearchRep

import scala.concurrent.duration._

trait ElasticsearchBoot {
  given materializer: Materializer

  val connectionSettings = ElasticsearchConnectionSettings("http://localhost:9200")
  val elasticsearchParamsV7 = ElasticsearchParams.V7("collatioindex")
  val sinkSettings = ElasticsearchWriteSettings(connectionSettings)
      .withBufferSize(10)
      .withVersionType("internal")
      .withRetryLogic(RetryAtFixedRate(maxRetries = 5, retryInterval = 1.second))
      .withApiVersion(ApiVersion.V7)

  val sourceSettings = ElasticsearchSourceSettings(connectionSettings)
      .withBufferSize(10)
      .withScrollDuration(5.minutes)
    
  val elasticsearchRep = new ElasticsearchRep(elasticsearchParamsV7, sinkSettings, sourceSettings)
}