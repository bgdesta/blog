package edu.miu.cs544.network.repository;

import edu.miu.cs544.network.domain.Follow;
import edu.miu.cs544.network.domain.FollowedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByUserId(Long userId);
    List<Follow> findByUserIdAndFollowedType(Long userId, FollowedType type);
}
