package org.acme.client;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

public class AuthBearerHeader implements ClientHeadersFactory {

    private final String IntegrationPrefix;

    public AuthBearerHeader(String IntegrationPrefix) {
        this.IntegrationPrefix = IntegrationPrefix;
    }

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add("Content-Type", "application/json");
        if (incomingHeaders.containsKey("Authorization")){
            headers.add("Authorization", incomingHeaders.getFirst("Authorization"));
        }

        return headers;}
}
