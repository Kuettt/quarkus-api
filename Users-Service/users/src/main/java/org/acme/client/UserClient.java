package org.acme.client;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class UserClient {

    @Inject
    @RestClient
    KeycloakClient keycloakClient;

    @ConfigProperty(name = "keycloak.admin.username")
    String adminUsername;

    @ConfigProperty(name = "keycloak.admin.password")
    String adminPassword;

    @ConfigProperty(name = "keycloak.admin.client-id")
    String adminClientId;

    @ConfigProperty(name = "keycloak.admin.realm")
    String realm;

    public void createUserInKeyCloak(String username, String password) {
        //admin token
            Map<String, Object> tokenResponse = keycloakClient.getToken(
                    "password",
                    adminClientId,
                    adminUsername,
                    adminPassword
            );

            String token = "Bearer " + tokenResponse.get("access_token");

            Map<String, Object> newUser = new HashMap<>();
            newUser.put("username", username);
            newUser.put("enabled", true);

            newUser.put("credentials", new Object[]{
                    Map.of(
                            "type", "password",
                            "value", password,
                            "temporary", false
                    )
        });

            try{
                keycloakClient.createUser(token, realm, newUser);
                Log.info("Usuário criado com sucesso no Keycloak" + username);

            } catch (Exception e) {
                Log.error("Erro ao criar usuário no Keycloak" + e.getMessage());
            }
    }
}
