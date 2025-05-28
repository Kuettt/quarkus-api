package org.acme.dto;

import java.time.Instant;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {

    private Long jobId;

    private String title;

    private String companyName;

    private String url;

    private Instant firstPublished;

    private Instant updateAt;

    private LocalDateTime lastSync;

}
