package edu.miu.cs544.identityprovider.controller;

import edu.miu.cs544.identityprovider.domain.Role;
import edu.miu.cs544.identityprovider.domain.Scope;
import edu.miu.cs544.identityprovider.dto.*;
import edu.miu.cs544.identityprovider.jwt.JwtTokenUtil;
import edu.miu.cs544.identityprovider.service.UserSecurityService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class UserSecurityController {
    private final static Logger log = LoggerFactory.getLogger(UserSecurityController.class);
    private final UserSecurityService userSecurityService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/me")
    public ResponseEntity<UserReadDto> getCurrentUser() {
        return ResponseEntity.ok(userSecurityService.currentUserDto());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Long userId = userSecurityService.currentUserDto().getId();
        List<RoleDto> roles = userSecurityService.getUserRoles(userId);
        String role = "";
        if(roles.size() > 0) {
            role = roles.get(0).getName();
        }
        List<Long> authorizedScopes = userSecurityService.getUserAuthorizedScopes(userId).stream()
                .map(scopeDto -> scopeDto.getId()).collect(Collectors.toList());
        HashMap<String, Object> claims = new HashMap<>();
        System.out.println("From logindto :" + loginDto.getUserName());
        claims.put("userName", loginDto.getUserName());
        System.out.println("From claims controller: " + claims.get("userName"));
        claims.put("userId", userId);
        claims.put("role", role);
        claims.put("scopes", authorizedScopes);
        String token = jwtTokenUtil.generateJwtToken(claims);
        return ResponseEntity.ok(
                new JwtTokenDto(token, "Bearer", userId, role , authorizedScopes)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<UserReadDto> createUser(@Valid @RequestBody UserCreateDto UserDto) {
        Optional<UserReadDto> userReadDto = userSecurityService.createUser(UserDto);
        if(userReadDto.isPresent()) {
            return ResponseEntity.ok(userReadDto.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
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
    public ResponseEntity forgotPassword(@Valid @RequestBody String email) {
        if(userSecurityService.forgotPassword(email)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestParam String code, @Valid @RequestBody String newPassword) {
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
    public ResponseEntity addRole(@PathVariable Long userId, @Valid @RequestBody IdDto roleId) {
        if(userSecurityService.addRoleToUser(userId, roleId.getId())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/users/{userId}/scopes/")
    public ResponseEntity addScope(@PathVariable Long userId, @Valid @RequestBody IdDto scopeId) {
        if(userSecurityService.addScopeToUser(userId, scopeId.getId())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
