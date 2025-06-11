package org.acme.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue
    private Long orderId;

    private Long userId;

    private String username;

    private String userEmail;

    private Long jobId;

    private String jobTitle;

    private String jobUrl;

    private LocalDateTime orderAt;

    @PrePersist
    public void prePersist()
    {
        this.orderAt = LocalDateTime.now();
    }

}
