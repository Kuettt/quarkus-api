package org.acme.filter;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.acme.services.AccessLogService;

import java.io.IOException;

@Provider
public class AccessLogFilter implements ContainerRequestFilter {

    @Inject
    SecurityIdentity identity;

    @Inject
    AccessLogService accessLogService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (identity.isAnonymous()) {
            return;
        }

        accessLogService.save(identity, requestContext);
    }



}
