package org.acme.service;

import org.acme.repository.JobsRepository;
import org.acme.dto.JobsDTO;
import org.acme.entity.JobsEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;



@ApplicationScoped
public class JobsService {

    @Inject
    private JobsRepository jobsRepository;
    
    ObjectMapper objectMapper = new ObjectMapper();
 
    //busca todos os trabalhos da lista 
    public List<JobsDTO> findAllJobs() {
        List<JobsDTO> jobs = new ArrayList<>();

        //lista todos os itens do banco de dados, torna eles manipuláveis com o stream, e após isto percorre a lista
        jobsRepository.findAll().stream().forEach(jobEntity -> {
        	//Ao trafegar os dados pra apresentar ao usuário é utilizado o tipo DTO (Data Transfer Object)
        	jobs.add(mapJobsEntityToDTO(jobEntity));
        });

        return jobs;
    }
    // cria um trabalho
    public void createNewJob(JobsDTO jobsDTO) {
    	//o jobsrepository só aceita objetos Entity, por isso a conversão foi necessária para guardar no DB
    	jobsRepository.persist(mapDTOToJobsEntity(jobsDTO));
    }
    
    //atualiza um trabalho por ID
    public void changeJob(long id, JobsDTO jobDTO){
    	
    	JobsEntity job = jobsRepository.findById(id);
    	
    	job.setJobId(jobDTO.getJobId());
    	job.setTitle(jobDTO.getTitle());
    	job.setCompanyName(jobDTO.getCompanyName());
    	job.setUrl(jobDTO.getUrl());
    	job.setFirstPublished(jobDTO.getFirstPublished());
    	job.setLastSync(jobDTO.getLastSync());
    	
    	jobsRepository.persist(job);
    }
    
    //deleta um trabalho por ID
    public void deleteJob(long id) {
    	jobsRepository.deleteById(id);
    	
    	
    }

    //Mapeia objetos JobsDTO para JobsEntity (tipo aceito no banco de dados)
    private JobsEntity mapDTOToJobsEntity(JobsDTO jobs)
    {
    	JobsEntity jobsEntity = new JobsEntity();
    	
    	jobsEntity.setJobId(jobs.getJobId());
    	jobsEntity.setTitle(jobs.getTitle());
    	jobsEntity.setCompanyName(jobs.getCompanyName());
    	jobsEntity.setUrl(jobs.getUrl());
    	jobsEntity.setFirstPublished(jobs.getFirstPublished());
    	jobsEntity.setLastSync(jobs.getLastSync());
    	
    	return jobsEntity;
    	
    }
    
    //Mapeia objetos JobsEntity para JobsDTO (tipo que trafega os dados)
    private JobsDTO mapJobsEntityToDTO(JobsEntity jobs) {
	  
	  JobsDTO jobsDTO = new JobsDTO();
	  
	  jobsDTO.setJobId(jobs.getJobId());
	  jobsDTO.setTitle(jobs.getTitle());
	  jobsDTO.setCompanyName(jobs.getCompanyName());
	  jobsDTO.setUrl(jobs.getUrl());
	  jobsDTO.setFirstPublished(jobs.getFirstPublished());
	  jobsDTO.setLastSync(jobs.getLastSync());
	  
	  return jobsDTO;
	  
  }
  
    //Método que faz uma requisição na API da greenhouse para atualizar o banco de dados
    public void fetchAndSaveJobsFromApi() {
	    try {
	    	URL api_url = new URI("https://boards-api.greenhouse.io/v1/boards/nubank/jobs").toURL();
	    	HttpURLConnection conn = (HttpURLConnection) api_url.openConnection();
	    	conn.setRequestMethod("GET");
		  
	    	InputStream inputStream = conn.getInputStream();
	    	JsonNode rootNode = objectMapper.readTree(inputStream);
	    	JsonNode jobsArray = rootNode.get("jobs");
		  
	    	if (jobsArray != null && jobsArray.isArray()) {
	    		Iterator<JsonNode> elements = jobsArray.elements();
	    		while (elements.hasNext()) {
	    			JsonNode jobNode = elements.next();
				  
					  JobsEntity job = new JobsEntity();
					  job.setJobId(jobNode.get("id").asLong());
					  job.setTitle(jobNode.get("title").asText());
					  job.setCompanyName("Nubank");
					  job.setUrl(jobNode.get("absolute_url").asText());
					  
					  
					  String updatedAtStr = jobNode.get("updated_at").asText();
					  Instant updateAt = Instant.parse(updatedAtStr);
					  job.setUpdateAt(updateAt);
					  job.setFirstPublished(updateAt);
					  
					  job.setLastSync(LocalDateTime.now(ZoneOffset.UTC));
					  
					  if (jobsRepository.findById(job.getJobId()) == null) {
					      jobsRepository.persist(job);
					  }
			  }
		  }
		  
	    	inputStream.close();
	    	conn.disconnect();
		  
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
  }
}

