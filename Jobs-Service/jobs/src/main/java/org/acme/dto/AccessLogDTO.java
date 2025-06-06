package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class AccessLogDTO {

    private String username;

    private String role;

    private String endpoint;

    private LocalDateTime date;
}
