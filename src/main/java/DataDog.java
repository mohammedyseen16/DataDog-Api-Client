// Submit metrics returns "Payload accepted" response
import com.datadog.api.client.ApiClient;
import com.datadog.api.client.ApiException;
import com.datadog.api.client.v2.api.MetricsApi;
import com.datadog.api.client.v2.model.IntakePayloadAccepted;
import com.datadog.api.client.v2.model.MetricIntakeType;
import com.datadog.api.client.v2.model.MetricPayload;
import com.datadog.api.client.v2.model.MetricPoint;
import com.datadog.api.client.v2.model.MetricResource;
import com.datadog.api.client.v2.model.MetricSeries;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class DataDog {
    static void Client() {
        // Replace with your Datadog API and App keys
        String api_key = "";
        String app_key = "";

        HashMap<String, String> Secret = new HashMap<String, String>();

        Secret.put("apiKeyAuth", api_key);
        Secret.put("appKeyAuth", app_key);

        System.out.println(Secret);

        ApiClient defaultClient = ApiClient.getDefaultApiClient();

        defaultClient.configureApiKeys(Secret);
        defaultClient.setBasePath("https://us5.datadoghq.com/");
        MetricsApi apiInstance = new MetricsApi(defaultClient);


//        System.out.println(body);


        try {
            int i = 0;
            IntakePayloadAccepted result;
            while (i <= 1000){

                Random rand = new Random();
                int rand_int1 = rand.nextInt(1000);
                MetricPayload body =
                        new MetricPayload()
                                .series(
                                        Collections.singletonList(
                                                new MetricSeries()
                                                        .metric("test.Yass")
                                                        .type(MetricIntakeType.UNSPECIFIED)
                                                        .points(
                                                                Collections.singletonList(
                                                                        new MetricPoint()
                                                                                .timestamp(OffsetDateTime.now().toInstant().getEpochSecond())
                                                                                .value(((double)rand_int1))))
                                                        .resources(
                                                                Collections.singletonList(
                                                                        new MetricResource().name("localhost").type("host")))));

//                System.out.println(body);


                result = apiInstance.submitMetrics(body);
//                System.out.println(result);
                i ++;
            }


        } catch (ApiException e) {
            System.err.println("Exception when calling MetricsApi#submitMetrics");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
