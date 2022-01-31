package edu.miu.cs544.network.service;

import edu.miu.cs544.network.domain.Follow;
import edu.miu.cs544.network.domain.FollowedType;
import edu.miu.cs544.network.dto.FollowDto;
import edu.miu.cs544.network.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{
    private final static Logger log = LoggerFactory.getLogger(FollowServiceImpl.class);

    private final FollowRepository followRepository;
    private final ModelMapper mapper;

    @Override
    public List<FollowDto> findAll() {
        List<Follow> follows = followRepository.findAll();
        return follows.stream().map(r -> mapper.map(r, FollowDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<FollowDto> findUserAllFollows(Long userId) {
        List<Follow> follows = followRepository.findByUserId(userId);
        return follows.stream().map(follow -> mapper.map(follow, FollowDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<FollowDto> findUserTopicFollows(Long userId) {
        List<Follow> follows = followRepository.findByUserIdAndFollowedType(userId, FollowedType.TOPIC);
        return follows.stream().map(follow -> mapper.map(follow, FollowDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<FollowDto> findUserBloggerFollows(Long userId) {
        List<Follow> follows = followRepository.findByUserIdAndFollowedType(userId, FollowedType.BLOGGER);
        return follows.stream().map(follow -> mapper.map(follow, FollowDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<FollowDto> findAll(Pageable pageable) {
        Page<Follow> follows = followRepository.findAll(pageable);
        return follows.stream().map(r -> mapper.map(r, FollowDto.class)).collect(Collectors.toList());
    }

    @Override
    public FollowDto findById(Long id) {
        Follow follow = followRepository.findById(id).orElseThrow();
        return mapper.map(follow, FollowDto.class);
    }

    @Override
    public FollowDto save(FollowDto dto) {
        Follow follow = mapper.map(dto, Follow.class);
        follow.setCreatedAt(LocalDateTime.now());
        followRepository.save(follow);
        log.info("New follow created: " + follow);
        return mapper.map(follow, FollowDto.class);
    }

    @Override
    public void delete(Long id) {
        followRepository.deleteById(id);
        log.info("Follow deleted id: " + id);
    }
}
