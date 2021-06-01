package ru.finance.analyst.entity.elastic

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object JsonHelper extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val itemFormat: RootJsonFormat[MonitoringTask] = jsonFormat7(MonitoringTask)
}
