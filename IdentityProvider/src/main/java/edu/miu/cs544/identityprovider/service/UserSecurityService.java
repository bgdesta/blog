package edu.miu.cs544.identityprovider.service;

import edu.miu.cs544.identityprovider.domain.User;
import edu.miu.cs544.identityprovider.dto.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserSecurityService extends UserDetailsService {

    Optional<UserReadDto> createUser(UserCreateDto userDto);

    UserReadDto currentUserDto();
    User currentUser();
    boolean changePassword(String currentPassword, String newPassword);
    boolean forgotPassword(String email);

    boolean resetPassword(String code, String newPassword);

    boolean verifyEmail(String verificationCode);
    boolean setUserEnable(Long userId, boolean enable);
    boolean setUserLock(Long userId, boolean lock);
    void setUserExpiration(LocalDateTime expiration);
    void setPasswordExpiration(LocalDateTime expiration);

    List<RoleDto> getUserRoles(Long id);

    List<ScopeDto> getUserScopes(Long id);

    List<ScopeDto> getUserAuthorizedScopes(Long id);

    boolean addRoleToUser(Long userId, Long roleId);
    boolean addScopeToUser(Long userId, Long scopeId);
    boolean removeRoleFromUser(Long userId, Long roleId);
    boolean removeScopeFromUser(Long userId, Long scopeId);
}
