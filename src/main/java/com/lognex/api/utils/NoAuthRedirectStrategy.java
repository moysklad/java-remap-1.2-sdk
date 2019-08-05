package com.lognex.api.utils;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;

import java.net.URI;
import java.util.Arrays;

public class NoAuthRedirectStrategy extends DefaultRedirectStrategy {
    @Override
    public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
        URI uri = getLocationURI(request, response, context);
        String method = request.getRequestLine().getMethod();

        if (response.getStatusLine().getStatusCode() == 303 && method.equals(HttpPost.METHOD_NAME)) {
            return new HttpGet(uri) {
                @Override
                public void setHeaders(Header[] headers) {
                    super.setHeaders(Arrays.stream(headers)
                            .filter(h -> !h.getName().equals("Authorization"))
                            .toArray(Header[]::new)
                    );
                }
            };
        }

        return super.getRedirect(request, response, context);
    }
}
