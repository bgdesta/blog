package edu.miu.cs544.identityprovider.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
}
