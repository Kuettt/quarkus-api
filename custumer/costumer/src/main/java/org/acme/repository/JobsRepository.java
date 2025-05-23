package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import org.acme.entity.JobsEntity;


@ApplicationScoped
public class JobsRepository implements PanacheRepository<JobsEntity>{

}
