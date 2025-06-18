package org.acme.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.acme.client.JobClient;
import org.acme.client.UserClient;
import org.acme.dto.JobInterestDTO;
import org.acme.dto.UserDTO;
import org.acme.entity.JobInterestEntity;
import org.acme.provider.ServiceTokenProvider;
import org.acme.repository.JobInterestRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class JobInterestService {

    @Inject
    private JobInterestRepository jobInterestRepository;

    @Inject
    @RestClient
    private UserClient userClient;

    @Inject
    @RestClient
    private JobClient jobClient;

    @Inject
    ServiceTokenProvider tokenProvider;

    public List<JobInterestDTO> getAllJobsInterests()
    {
        List<JobInterestDTO> jobsInterests = new ArrayList<>();
        jobInterestRepository.findAll().stream().forEach(requestLog ->
                jobsInterests.add(mapOrderEntityToDTO(requestLog)));

        return jobsInterests;
    }

    public void saveOrder(JobInterestDTO jobInterestDTO){

        String token = "Bearer " + tokenProvider.getAccessToken();
        UserDTO userDTO =  userClient.findUserById(token, jobInterestDTO.getUserId());

        if (userDTO.getUsername().equals(jobInterestDTO.getUsername()) && jobClient.findJobById(jobInterestDTO.getJobId()) != null) {
            jobInterestRepository.persist(mapDTOToOrderEntity(jobInterestDTO));
        } else {
            throw new NotFoundException();
        }

    }


    private JobInterestDTO mapOrderEntityToDTO(JobInterestEntity jobInterestEntity){

        JobInterestDTO jobInterestDTO = new JobInterestDTO();

        jobInterestDTO.setUserId(jobInterestEntity.getUserId());
        jobInterestDTO.setUsername(jobInterestEntity.getUsername());
        jobInterestDTO.setUserEmail(jobInterestEntity.getUserEmail());
        jobInterestDTO.setJobId(jobInterestEntity.getJobId());
        jobInterestDTO.setJobTitle(jobInterestEntity.getJobTitle());
        jobInterestDTO.setJobUrl(jobInterestEntity.getJobUrl());

        return jobInterestDTO;
    }

    private JobInterestEntity mapDTOToOrderEntity(JobInterestDTO jobInterestDTO)
    {
        JobInterestEntity jobInterestEntity = new JobInterestEntity();

        jobInterestEntity.setUserId(jobInterestDTO.getUserId());
        jobInterestEntity.setUsername(jobInterestDTO.getUsername());
        jobInterestEntity.setUserEmail(jobInterestDTO.getUserEmail());
        jobInterestEntity.setJobId(jobInterestDTO.getJobId());
        jobInterestEntity.setJobTitle(jobInterestDTO.getJobTitle());
        jobInterestEntity.setJobUrl(jobInterestDTO.getJobUrl());

        return jobInterestEntity;
    }


}
