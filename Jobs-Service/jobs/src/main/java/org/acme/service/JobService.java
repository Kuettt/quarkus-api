package org.acme.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.client.JobClient;
import org.acme.dto.JobDTO;
import org.acme.repository.JobRepository;
import org.acme.entity.JobEntity;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@ApplicationScoped
public class JobService {

    @Inject
    JobRepository jobRepository;

    @Inject
    @RestClient
    JobClient jobClient;

    ObjectMapper objectMapper = new ObjectMapper();

    public List<JobDTO> findAllJobs(){

        List<JobDTO> jobs = new ArrayList<>();

        jobRepository.listAll().stream().forEach(job -> {
            jobs.add(mapJobEntityToDTO(job));
        });

        return jobs;
    }

    public JobDTO findJobById(Long id){
        JobEntity jobEntity = jobRepository.findById(id);
        return mapJobEntityToDTO(jobEntity);
    }

    public void updateJob(Long id,JobDTO jobDTO){

        JobEntity jobEntity = jobRepository.findById(id);

        jobEntity.setJobId(jobDTO.getJobId());
        jobEntity.setTitle(jobDTO.getTitle());
        jobEntity.setCompanyName(jobDTO.getCompanyName());
        jobEntity.setUrl(jobDTO.getUrl());
        jobEntity.setFirstPublished(jobDTO.getFirstPublished());
        jobEntity.setLastSync(jobDTO.getLastSync());

        jobRepository.persist(jobEntity);
    }

    public void RegisterNewJob(JobDTO jobDTO){
        jobRepository.persist(mapDTOToJobEntity(jobDTO));
    }




    private JobDTO mapJobEntityToDTO(JobEntity jobEntity){

        JobDTO jobDTO = new JobDTO();

        jobDTO.setJobId(jobEntity.getJobId());
        jobDTO.setTitle(jobEntity.getTitle());
        jobDTO.setCompanyName(jobEntity.getCompanyName());
        jobDTO.setUrl(jobEntity.getUrl());
        jobDTO.setFirstPublished(jobEntity.getFirstPublished());
        jobDTO.setLastSync(jobEntity.getLastSync());

        return jobDTO;
    }

    private JobEntity mapDTOToJobEntity(JobDTO jobDTO)
    {
        JobEntity jobEntity = new JobEntity();

        jobEntity.setJobId(jobDTO.getJobId());
        jobEntity.setTitle(jobDTO.getTitle());
        jobEntity.setCompanyName(jobDTO.getCompanyName());
        jobEntity.setUrl(jobDTO.getUrl());
        jobEntity.setFirstPublished(jobDTO.getFirstPublished());
        jobEntity.setLastSync(jobDTO.getLastSync());

        return jobEntity;
    }



    public void syncJobsFromApi(){
        JsonNode response = jobClient.fetchJobs();

        if (response.has("jobs")){
            for (JsonNode jobNode: response.get("jobs")){
                JobEntity jobEntity = new JobEntity();

                jobEntity.setJobId(jobNode.get("id").asLong());
                jobEntity.setTitle(jobNode.get("title").asText());
                jobEntity.setCompanyName(jobNode.get("company_name").asText());
                jobEntity.setUrl(jobNode.get("absolute_url").asText());

                String updatedAtStr = jobNode.get("updated_at").asText();
                Instant updatedAt = Instant.parse(updatedAtStr);

                String publishedAtStr = jobNode.get("first_published").asText();
                Instant publishedAt = Instant.parse(publishedAtStr);

                jobEntity.setUpdateAt(updatedAt);
                jobEntity.setFirstPublished(publishedAt);
                jobEntity.setLastSync(LocalDateTime.now(ZoneOffset.UTC));


                if (jobRepository.findById(jobEntity.getJobId()) == null) {
                    jobRepository.persist(jobEntity);
                }

            }
        }
    }

}
