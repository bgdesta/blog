package edu.miu.cs544.identityprovider.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordDto {
    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;
}
