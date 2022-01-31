package edu.miu.cs544.network.service;

import edu.miu.cs544.network.dto.FollowDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowService {
    List<FollowDto> findAll();
    List<FollowDto> findUserAllFollows(Long userId);
    List<FollowDto> findUserTopicFollows(Long userId);
    List<FollowDto> findUserBloggerFollows(Long userId);
    List<FollowDto> findAll(Pageable pageable);
    FollowDto findById(Long id);
    FollowDto save(FollowDto dto);
    void delete(Long id);
}
