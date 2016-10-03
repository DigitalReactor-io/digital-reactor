package io.digitalreactor.vendor.yandex;

import io.digitalreactor.vendor.yandex.serivce.CounterApiService;
import io.digitalreactor.vendor.yandex.serivce.GoalApiService;
import io.digitalreactor.vendor.yandex.serivce.GrantCodeToTokenResolver;
import io.digitalreactor.vendor.yandex.serivce.ReportApiService;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by MStepachev on 09.09.2016.
 */
@Configuration
@PropertySource("classpath:yandex.properties")
public class YandexServiceConfig {

    @Value("${vendor.yandex.oauth.url}")
    private String oauthUrl;
    @Value("${vendor.yandex.api.url}")
    private String apiUrl;
    @Value("${vendor.yandex.api.client.secret}")
    private String clientSecret;
    @Value("${vendor.yandex.api.application.id}")
    private String applicationId;
    @Value("${vendor.yandex.api.application.auth}")
    private String applicationAuth;

    @Bean
    public CloseableHttpClient closeableHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return HttpClients.custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, (TrustStrategy) (arg0, arg1) -> true).build())
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
    }

    @Bean
    public GrantCodeToTokenResolver grantCodeToTokenResolver(CloseableHttpClient httpClient) {
        return new GrantCodeToTokenResolver(httpClient, oauthUrl, applicationId, applicationAuth, clientSecret);
    }

    @Bean
    public CounterApiService counterApiService(CloseableHttpClient httpClient) {
        return new CounterApiService(httpClient, apiUrl,  applicationAuth);
    }

    @Bean
    public GoalApiService goalApiService(CloseableHttpClient httpClient) {
        return new GoalApiService(httpClient, apiUrl,  applicationAuth);
    }

    @Bean
    public ReportApiService reportApiService(CloseableHttpClient httpClient) {
        return new ReportApiService(httpClient);
    }

}
