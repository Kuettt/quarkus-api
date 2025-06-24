package org.acme.filter;


import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;

import java.io.IOException;

public class UserClientFilter implements ClientResponseFilter {
    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        System.out.println("raw path: " + requestContext.getUri().getRawPath());
        System.out.println("host: " + requestContext.getUri().getHost());
        System.out.println("scheme: " + requestContext.getUri().getScheme());
        System.out.println("path: " + requestContext.getUri().getPath());
        System.out.println("headers: " + requestContext.getStringHeaders().toString());
    }


}
