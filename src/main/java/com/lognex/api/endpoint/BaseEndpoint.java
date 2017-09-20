package com.lognex.api.endpoint;

import com.lognex.api.API;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@Slf4j
public abstract class BaseEndpoint {

    protected String executeGet(final String httpsUrl, final API.RequestBuilder rb) {
        try (CloseableHttpClient httpclient = buildHttpClient(rb.login(), rb.password());
             CloseableHttpResponse response = httpclient.execute(new HttpGet(httpsUrl))) {
                return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error("Error: ", e);
            return "";
        }
    }

    private CloseableHttpClient buildHttpClient(String login, String password) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(login, password));
        return HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
    }
}
