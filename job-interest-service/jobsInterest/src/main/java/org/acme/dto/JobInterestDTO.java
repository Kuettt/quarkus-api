package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobInterestDTO {

    private Long userId;

    private String username;

    private String userEmail;

    private Long jobId;

    private String jobTitle;

    private String jobUrl;
}
