package edu.miu.cs544.network.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Follow {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long followedId;
    @Enumerated(EnumType.STRING)
    private FollowedType followedType;
    private LocalDateTime createdAt;
}
