package edu.miu.cs544.identityprovider.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String userName;
    private String password;
}
