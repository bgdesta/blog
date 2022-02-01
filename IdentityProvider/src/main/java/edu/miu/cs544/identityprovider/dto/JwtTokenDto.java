package edu.miu.cs544.identityprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtTokenDto {
    private String accessToken;
    private String type ;
    private Long userId;
    private String role;
    private List<Long> authorizedScopes;
}
