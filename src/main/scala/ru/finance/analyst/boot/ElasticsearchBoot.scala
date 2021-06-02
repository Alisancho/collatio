package ru.finance.analyst.boot

import akka.stream.Materializer
import akka.stream.alpakka.elasticsearch.{ApiVersion, ElasticsearchConnectionSettings, ElasticsearchParams, ElasticsearchSourceSettings, ElasticsearchWriteSettings, RetryAtFixedRate}
import ru.finance.analyst.config.Config.ElasticConfig.host

import scala.concurrent.duration._

trait ElasticsearchBoot {
  implicit val materializer: Materializer

  val connectionSettings: ElasticsearchConnectionSettings = ElasticsearchConnectionSettings(host)
  val elasticsearchParamsV7: ElasticsearchParams          = ElasticsearchParams.V7("collatioindex")
  val sinkSettings: ElasticsearchWriteSettings            = ElasticsearchWriteSettings(connectionSettings)
    .withBufferSize(10)
    .withVersionType("internal")
    .withRetryLogic(RetryAtFixedRate(maxRetries = 5, retryInterval = 1.second))
    .withApiVersion(ApiVersion.V7)

  val sourceSettings: ElasticsearchSourceSettings = ElasticsearchSourceSettings(connectionSettings)
    .withBufferSize(10)
    .withScrollDuration(5.minutes)

}
