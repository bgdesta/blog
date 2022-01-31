package edu.miu.cs544.identityprovider.controller;

import edu.miu.cs544.identityprovider.domain.Scope;
import edu.miu.cs544.identityprovider.dto.*;
import edu.miu.cs544.identityprovider.jwt.JwtTokenUtil;
import edu.miu.cs544.identityprovider.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class UserSecurityController {
    private final UserSecurityService userSecurityService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/me")
    public ResponseEntity<UserReadDto> getCurrentUser() {
        return ResponseEntity.ok(userSecurityService.currentUserDto());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateJwtToken(authentication);
        UserReadDto userDto = userSecurityService.currentUserDto();
        List<Long> authorizedScopes = userSecurityService.getUserAuthorizedScopes(userDto.getId()).stream()
                .map(scopeDto -> scopeDto.getId()).collect(Collectors.toList());
        return ResponseEntity.ok(
                new JwtTokenDto(token, userDto, "Bearer", authorizedScopes)
        );
    }

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        if(userSecurityService.changePassword(changePasswordDto.getCurrentPassword(), changePasswordDto.getNewPassword())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/verify")
    public ResponseEntity verify(@RequestParam String code) {
        if (userSecurityService.verifyEmail(code)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity forgotPassword(@RequestBody String email) {
        if(userSecurityService.forgotPassword(email)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestParam String code, @RequestBody String newPassword) {
        if(userSecurityService.resetPassword(code, newPassword)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/users/{id}/roles/")
    public ResponseEntity<List<RoleDto>> getRoles(@PathVariable Long id) {
        return ResponseEntity.ok(userSecurityService.getUserRoles(id));
    }

    @GetMapping("/users/{id}/scopes/")
    public ResponseEntity<List<ScopeDto>> getScopes(@PathVariable Long id) {
        return ResponseEntity.ok(userSecurityService.getUserScopes(id));
    }

    @PostMapping("/users/{userId}/roles/")
    public ResponseEntity addRole(@PathVariable Long userId, @RequestBody IdDto roleId) {
        if(userSecurityService.addRoleToUser(userId, roleId.getId())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/users/{userId}/scopes/")
    public ResponseEntity addScope(@PathVariable Long userId, @RequestBody IdDto scopeId) {
        if(userSecurityService.addScopeToUser(userId, scopeId.getId())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
