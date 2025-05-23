package org.acme.entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name="job")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobsEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Long jobId;
	
	private String title;
	
	private String companyName;
	
	private String url;
	
	private Instant firstPublished;
	
	private Instant updateAt;
	
	private LocalDateTime lastSync;
	
}
