package io.digitalreactor.vendor.yandex.serivce;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MStepachev on 09.09.2016.
 */
public class GrantCodeToTokenResolverTest {

    private GrantCodeToTokenResolver tokenResolver;

    @Before
    public void setUp() throws Exception {

        tokenResolver = new GrantCodeToTokenResolver(
                HttpClients.custom()
                        .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, (TrustStrategy) (arg0, arg1) -> true).build())
                        .setSSLHostnameVerifier(new NoopHostnameVerifier())
                        .build(),
                "oauth.yandex.ru",
                "b26e324d5a134168b090b3f23e77a0e7",
                "Basic YjI2ZTMyNGQ1YTEzNDE2OGIwOTBiM2YyM2U3N2EwZTc6Yjk3MGJjMWIzOGI3NDE5YWEyN2Y4YjhjM2Q1ZDEzZTA=",
                "b970bc1b38b7419aa27f8b8c3d5d13e0"
        );

    }

    @Test
    public void getTokenByGrantCode() throws Exception {
        //https://api-metrika.yandex.ru/management/v1/counters?oauth_token=AQAAAAAUQvf-AAK_6bPrzT-_lEJUn03N3tAjmJ8
        tokenResolver.getTokenByGrantCode(7032653L);
    }

}