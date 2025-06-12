package org.acme.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public class JobDTO {
    private Long jobId;

    private String title;

    private String companyName;

    private String url;

    private Instant firstPublished;

    private Instant updateAt;

    private LocalDateTime lastSync;
}
