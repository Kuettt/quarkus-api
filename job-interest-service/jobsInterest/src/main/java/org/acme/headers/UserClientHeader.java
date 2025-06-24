package org.acme.headers;


import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

public class UserClientHeader implements ClientHeadersFactory{

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> inputRequest, MultivaluedMap<String, String> OutputResponse) {


        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", inputRequest.get("Authorization").get(0));
        return headers;
    }
}
