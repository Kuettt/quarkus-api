package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.AccessLogEntity;

@ApplicationScoped
public class AccessLogRepository implements PanacheRepository<AccessLogEntity> {

}
