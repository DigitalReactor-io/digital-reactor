package io.digitalreactor.vendor.yandex.serivce;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.digitalreactor.vendor.yandex.model.Counter;
import io.digitalreactor.vendor.yandex.model.CounterResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by MStepachev on 09.09.2016.
 */
public class CounterApiService {
    private static final Logger logger = LoggerFactory.getLogger(CounterApiService.class);

    public static final String COUNTETS = "/management/v1/counters?oauth_token=";

    private final String applicationAuth;
    private final String apiUrl;

    private final CloseableHttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public CounterApiService(CloseableHttpClient httpClient, String apiUrl, String applicationAuth) {
        this.applicationAuth = applicationAuth;
        this.apiUrl = apiUrl;
        this.httpClient = httpClient;
    }

    public List<Counter> getCounters(String clientToken) {
        //TODO[St.maxim] error resolve
        try {
            CounterResponse counterResponse = getCounterResponse(request(clientToken));

            return counterResponse.getCounters();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //&oauth_token=
        return null;
    }

    private HttpGet request(String accessToken) throws UnsupportedEncodingException {
        HttpGet httpGet = new HttpGet("https://" + apiUrl + COUNTETS + accessToken);
        httpGet.addHeader("Authorization", applicationAuth);

        return httpGet;
    }

    private CounterResponse getCounterResponse(HttpGet request) throws IOException {
        return httpClient.execute(request, response -> {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return mapper.readValue(response.getEntity().getContent(), CounterResponse.class);
            } else {
                //TODO[St.maxim] custom exception
                throw new RuntimeException("Gotten: " + statusCode + IOUtils.toString(response.getEntity().getContent(), "UTF-8"));
            }
        });
    }
}
