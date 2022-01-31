package edu.miu.cs544.identityprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtTokenDto {
    private String accessToken;
    private UserReadDto user;
    private String type ;
    private List<Long> authorizedScopes;
}
