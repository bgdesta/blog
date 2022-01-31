package edu.miu.cs544.network.controller;

import edu.miu.cs544.network.dto.FollowDto;
import edu.miu.cs544.network.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;

    @GetMapping("/")
    public ResponseEntity<List<FollowDto>> getAll() {
        return ResponseEntity.ok(followService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<FollowDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(followService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody FollowDto followDto) {
        return ResponseEntity.ok(followService.save(followDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        followService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}/follows/")
    public ResponseEntity<List<FollowDto>> getUserAllFollowing(@PathVariable Long id) {
        return ResponseEntity.ok(followService.findUserAllFollows(id));
    }

    @GetMapping("/users/{id}/follows/topics/")
    public ResponseEntity<List<FollowDto>> getUserFollowingTopics(@PathVariable Long id) {
        return ResponseEntity.ok(followService.findUserTopicFollows(id));
    }

    @GetMapping("/users/{id}/follows/bloggers")
    public ResponseEntity<List<FollowDto>> getUserFollowingBloggers(@PathVariable Long id) {
        return ResponseEntity.ok(followService.findUserBloggerFollows(id));
    }
}
