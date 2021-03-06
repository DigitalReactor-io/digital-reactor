package io.digitalreactor.vendor.yandex.serivce;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.digitalreactor.vendor.yandex.domain.CustomRequest;
import io.digitalreactor.vendor.yandex.domain.Request;
import io.digitalreactor.vendor.yandex.model.Response;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by MStepachev on 22.09.2016.
 */
public class ReportApiService {

    private static final Logger logger = LoggerFactory.getLogger(ReportApiService.class);

    private final CloseableHttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public ReportApiService(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Response findAllBy(Request request) {
        try {
            return httpClient.execute(request(request.toQuery()), response -> {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    String jasonResponse = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                    logger.debug(jasonResponse);

                    return mapper.readValue(jasonResponse, Response.class);
                } else {
                    //TODO[St.maxim] custom exception
                    throw new RuntimeException("Gotten: " + statusCode + IOUtils.toString(response.getEntity().getContent(), "UTF-8"));
                }
            });

        } catch (UnsupportedEncodingException e) {
            //TODO[St.maxim] fix it
            logger.error("Can't parse query {}", request.toQuery());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <R> R findAllBy(CustomRequest<String, R> request) {
        try {
            return httpClient.execute(request(request.toQuery()), response -> {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    String jasonResponse = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                    logger.debug(jasonResponse);

                    return request.transform(jasonResponse);
                } else {
                    //TODO[St.maxim] custom exception
                    throw new RuntimeException("Gotten: " + statusCode + IOUtils.toString(response.getEntity().getContent(), "UTF-8"));
                }
            });

        } catch (UnsupportedEncodingException e) {
            //TODO[St.maxim] fix it
            logger.error("Can't parse query {}", request.toQuery());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpGet request(String query) throws UnsupportedEncodingException {
        HttpGet httpGet = new HttpGet("https://api-metrika.yandex.ru/" + query);

        return httpGet;
    }
}
