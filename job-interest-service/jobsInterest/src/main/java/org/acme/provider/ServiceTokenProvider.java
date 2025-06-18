package org.acme.provider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.client.KeycloakTokenClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Map;

@ApplicationScoped
public class ServiceTokenProvider {

    @Inject
    @RestClient
    KeycloakTokenClient keycloakTokenClient;

    @ConfigProperty(name = "keycloak.client.id")
    String clientId;

    @ConfigProperty(name = "keycloak.client.secret")
    String clientSecret;

    public String getAccessToken() {
        Map<String, Object> response = keycloakTokenClient.getToken(
                "client_credentials", clientId, clientSecret
        );

        return (String) response.get("access_token");
    };
}
