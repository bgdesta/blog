package edu.miu.cs544.identityprovider.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
public class SecurityCode {
    private String code;
    private LocalDateTime expiration;
}
