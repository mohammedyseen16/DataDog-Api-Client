package org.jack

import com.datadog.api.client.ApiClient
import com.datadog.api.client.v2.api.MetricsApi
import com.datadog.api.client.v2.model.{IntakePayloadAccepted, MetricIntakeType, MetricPayload, MetricPoint, MetricResource, MetricSeries, MetricTagConfigurationType}

import java.time.OffsetDateTime
import java.util
import java.util.{Collections, HashMap}

object DataDogHelper {
  def main(args: Array[String]): Unit = {

    val api_key = ""
    val app_key = ""
    val url = "https://us5.datadoghq.com/"

    val body = new MetricPayload()
      .series(Collections.singletonList(new MetricSeries()
        .metric("jack.SYS")
        .`type`(MetricIntakeType.UNSPECIFIED)
        .points(Collections.singletonList(new MetricPoint()
          .timestamp(OffsetDateTime.now.toInstant.getEpochSecond)
          .value(0.7)))
        .resources(Collections.singletonList(new MetricResource().name("localhost")
          .`type`("host")))))

    val Secret = new util.HashMap[String, String]
    val defaultClient = ApiClient.getDefaultApiClient
    val apiInstance = new MetricsApi(defaultClient)
    Secret.put("apiKeyAuth", api_key)
    Secret.put("appKeyAuth", app_key)
    defaultClient.configureApiKeys(Secret)
    defaultClient.setBasePath(url)

    val result = apiInstance.submitMetrics(body)
    println(result)

    println("hi")
  }

}
