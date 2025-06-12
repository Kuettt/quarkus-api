package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.JobInterestEntity;

@ApplicationScoped
public class JobInterestRepository implements PanacheRepository<JobInterestEntity> {
}
