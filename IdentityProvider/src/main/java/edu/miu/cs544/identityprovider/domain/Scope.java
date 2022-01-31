package edu.miu.cs544.identityprovider.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Scope {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private String service;

    @Enumerated(EnumType.STRING)
    private HttpMethod method;
    private String uri;
    private String scopeGroup;
    @OneToOne
    private User createdBy;
    private LocalDateTime createdAt;
}