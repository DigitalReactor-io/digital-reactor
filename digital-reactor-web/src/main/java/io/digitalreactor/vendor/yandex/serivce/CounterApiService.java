package io.digitalreactor.vendor.yandex.serivce;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.digitalreactor.vendor.yandex.model.Counter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by MStepachev on 09.09.2016.
 */
public class CounterApiService {
    private static final Logger logger = LoggerFactory.getLogger(CounterApiService.class);

    public static final String COUNTETS = "/management/v1/counters?";

    private final String applicationId;
    private final String applicationAuth;
    private final String clientSecret;
    private final String apiUrl;

    private final CloseableHttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public CounterApiService(CloseableHttpClient httpClient, String apiUrl, String applicationId, String applicationAuth, String clientSecret) {
        this.applicationId = applicationId;
        this.applicationAuth = applicationAuth;
        this.clientSecret = clientSecret;
        this.apiUrl = apiUrl;
        this.httpClient = httpClient;
    }

    public List<Counter> getCounters(String clientToken) {

        //&oauth_token=
        return null;
    }
}
