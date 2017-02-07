package io.digitalreactor.vendor.yandex.serivce;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by MStepachev on 09.09.2016.
 */
public class GrantCodeToTokenResolver {
    private static final Logger logger = LoggerFactory.getLogger(GrantCodeToTokenResolver.class);

    private final String TOKEN_RESOURCE = "/token";

    private final String applicationId;
    private final String applicationAuth;
    private final String clientSecret;
    private final String oauthUrl;

    private final CloseableHttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public GrantCodeToTokenResolver(CloseableHttpClient httpClient, String oauthUrl, String applicationId, String applicationAuth, String clientSecret) {
        this.applicationId = applicationId;
        this.applicationAuth = applicationAuth;
        this.clientSecret = clientSecret;
        this.oauthUrl = oauthUrl;
        this.httpClient = httpClient;
    }

    public String getTokenByGrantCode(Long grantCode) {
        TokenResponse token = null;
        try {
            token = getToken(request(grantCode));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO[St.maxim] verification and validation

        return token.getAccessToken();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class TokenResponse {
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expires_in")
        private String expiresIn;
        @JsonProperty("refresh_token")
        private String refreshToken;

        public TokenResponse() {
        }

        public String getAccessToken() {
            return accessToken;
        }
    }

    private HttpPost request(Long grantCode) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost("https://" + oauthUrl + TOKEN_RESOURCE);
        httpPost.addHeader("Authorization", applicationAuth);
        httpPost.setEntity(new StringEntity(requestBody(grantCode)));

        return httpPost;
    }

    private TokenResponse getToken(HttpPost tokenRequest)  throws IOException {
        return httpClient.execute(tokenRequest, response -> {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return mapper.readValue(response.getEntity().getContent(), TokenResponse.class);
            } else {
                //TODO[St.maxim] custom exception
                throw new RuntimeException("Gotten: " + statusCode);
            }
        });
    }

    private String requestBody(Long grantCode) {
        return "grant_type=authorization_code&code=" + grantCode +
                "&client_id=" + applicationId + "&client_secret=" + clientSecret;
    }
}
