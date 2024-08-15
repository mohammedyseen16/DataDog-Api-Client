import com.datadog.api.client.v2.model.{MetricIntakeType, MetricPayload, MetricPoint, MetricResource, MetricSeries}

import java.time.OffsetDateTime
import java.util.{Collections, Random}


object DataApiClinet {
  def main(args: Array[String]): Unit = {
    print("hello")

    val client = DataDog.Client();

    val rand = new Random
    val rand_int1 = rand.nextInt(1000)
    val body = new MetricPayload()
      .series(Collections
        .singletonList(new MetricSeries().metric("test.Yass")
          .`type`(MetricIntakeType.UNSPECIFIED)
          .points(Collections.singletonList(new MetricPoint()
            .timestamp(OffsetDateTime.now.toInstant.getEpochSecond)
            .value(rand_int1.toDouble)))
          .resources(Collections.singletonList(new MetricResource().name("localhost")
            .`type`("host")))))
  }
}
