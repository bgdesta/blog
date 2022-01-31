package edu.miu.cs544.identityprovider.dto;

import lombok.Data;
import org.springframework.http.HttpMethod;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ScopeDto {
    private Long id;
    private String name;
    private String description;
    private String service;
    private String method;
    private String uri;
    private String scopeGroup;
}
