package edu.miu.cs544.identityprovider.dto;

import lombok.Data;
import org.springframework.http.HttpMethod;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
public class ScopeDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    private String service;
    @NotNull
    private String method;
    @NotNull
    private String uri;
    private String scopeGroup;
}
