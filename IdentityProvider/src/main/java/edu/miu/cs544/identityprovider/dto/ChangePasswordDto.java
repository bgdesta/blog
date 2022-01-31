package edu.miu.cs544.identityprovider.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String currentPassword;
    private String newPassword;
}
