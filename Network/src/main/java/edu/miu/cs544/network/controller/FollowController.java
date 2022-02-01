package edu.miu.cs544.network.controller;

import edu.miu.cs544.network.dto.FollowDto;
import edu.miu.cs544.network.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;

    @GetMapping("/")
    @PreAuthorize("#role == 'Admin'")
    public ResponseEntity<List<FollowDto>> getAll(@RequestHeader String role) {
        System.out.println(role);
        return ResponseEntity.ok(followService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("#role == 'Consumer' or #role == 'Admin'")
    ResponseEntity<FollowDto> getOne(@RequestHeader String userId, @RequestHeader String role, @PathVariable Long id) {
        System.out.println(role);
        return ResponseEntity.ok(followService.findById(id));
    }

    @PostMapping("/")
    @PreAuthorize("#role == 'Consumer'")
    public ResponseEntity create(@RequestHeader String userId, @RequestHeader String role, @RequestBody FollowDto followDto) {
        return ResponseEntity.ok(followService.save(followDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#role == 'Consumer'")
    public ResponseEntity delete(@RequestHeader String userId, @RequestHeader String role, @PathVariable Long id) {
        followService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("#role == 'Consumer'")
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
