package edu.miu.cs544.identityprovider.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @NotNull
    private String userName;
    @NotNull
    private String password;
}
