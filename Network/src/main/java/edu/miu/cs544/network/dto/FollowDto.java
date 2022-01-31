package edu.miu.cs544.network.dto;

import edu.miu.cs544.network.domain.FollowedType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class FollowDto {
    private Long id;
    private Long userId;
    private Long followedId;
    private FollowedType followedType;
}
