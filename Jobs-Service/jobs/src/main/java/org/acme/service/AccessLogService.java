package org.acme.service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.acme.entity.AccessLogEntity;
import org.acme.repository.AccessLogRepository;

import java.time.LocalDateTime;

@ApplicationScoped
public class AccessLogService {

    @Inject
    AccessLogRepository accessLogRepository;

    @Transactional
    public void save(SecurityIdentity identity, ContainerRequestContext requestContext){

        AccessLogEntity log = new AccessLogEntity();


        boolean isAdmin = identity.hasRole("admin");
        boolean isUser = identity.hasRole("user");

        log.setUsername(identity.getPrincipal().getName());
        log.setRole(isAdmin ? "admin":"user");
        log.setEndpoint(requestContext.getUriInfo().getPath());
        log.setDate(LocalDateTime.now());

        accessLogRepository.persist(log);
    }


}
