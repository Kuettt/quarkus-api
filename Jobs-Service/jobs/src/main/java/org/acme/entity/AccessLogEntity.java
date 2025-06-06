package org.acme.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Table(name="LogEntity")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccessLogEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String role;

    private String endpoint;

    private LocalDateTime date;
}

