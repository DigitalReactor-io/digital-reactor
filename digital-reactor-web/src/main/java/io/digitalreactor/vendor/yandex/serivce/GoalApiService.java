package io.digitalreactor.vendor.yandex.serivce;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.digitalreactor.vendor.yandex.model.GoalResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by ingvard on 24.09.16.
 */
public class GoalApiService {

    private static final Logger logger = LoggerFactory.getLogger(GoalApiService.class);

    public static final String GOALS = "/management/v1/counter/%s/goals?oauth_token=%s";


    private final String applicationAuth;
    private final String apiUrl;

    private final CloseableHttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public GoalApiService(CloseableHttpClient httpClient, String apiUrl, String applicationAuth) {
        this.applicationAuth = applicationAuth;
        this.apiUrl = apiUrl;
        this.httpClient = httpClient;
    }

    public GoalResponse getGoals(String counterId, String accessToken) {
        try {
            return getGoalResponse(request(counterId, accessToken));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpGet request(String counterId, String accessToken) throws UnsupportedEncodingException {
        HttpGet httpGet = new HttpGet("https://" + apiUrl + String.format(GOALS, counterId, accessToken));
        httpGet.addHeader("Authorization", applicationAuth);

        return httpGet;
    }

    private GoalResponse getGoalResponse(HttpGet request) throws IOException {
        return httpClient.execute(request, response -> {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return mapper.readValue(response.getEntity().getContent(), GoalResponse.class);
            } else {
                //TODO[St.maxim] custom exception
                throw new RuntimeException("Gotten: " + statusCode + IOUtils.toString(response.getEntity().getContent(), "UTF-8"));
            }
        });
    }
}
