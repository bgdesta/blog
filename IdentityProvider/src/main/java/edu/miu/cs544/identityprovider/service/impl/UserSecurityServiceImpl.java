package edu.miu.cs544.identityprovider.service.impl;

import edu.miu.cs544.identityprovider.domain.*;
import edu.miu.cs544.identityprovider.dto.*;
import edu.miu.cs544.identityprovider.repository.RoleRepository;
import edu.miu.cs544.identityprovider.repository.ScopeRepository;
import edu.miu.cs544.identityprovider.repository.UserRepository;
import edu.miu.cs544.identityprovider.repository.UserSecurityRepository;
import edu.miu.cs544.identityprovider.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {
    private final static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final UserSecurityRepository userSecurityRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ScopeRepository scopeRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Searching for username: " + username);
        Optional<UserSecurity> optionalUser = userSecurityRepository.findByUserName(username);
        if(optionalUser.isEmpty()) {
            log.warn("User with username: " + username + " not found!");
            throw new UsernameNotFoundException("User Not Found!");
        }
        return optionalUser.get();
    }

    @Override
    public Optional<UserReadDto> createUser(UserCreateDto userDto) {
        User user = mapper.map(userDto, User.class);
        Optional<UserSecurity> optionalUser = userSecurityRepository.findByUserName(userDto.getEmail());
        if(optionalUser.isPresent()) {
            log.warn("User with email: " + userDto.getEmail() + " found!");
            return Optional.empty();
        }
        UserSecurity userSecurity = defaultUserSecurity(user.getEmail(), userDto.getPassword());
        user.setUserSecurity(userSecurity);
        Role role = defaultRole();
        userSecurity.addRole(role);
        userRepository.save(user);

        sendEmailVerification(user.getEmail(), userSecurity.getEmailVerificationCode().getCode());
        return Optional.of(mapper.map(user, UserReadDto.class));
    }

    private UserSecurity defaultUserSecurity(String userName, String password) {
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setEnabled(true);
        userSecurity.setLocked(false);
        userSecurity.setVerified(false);
        userSecurity.setAccountExpiration(LocalDateTime.now().plusYears(1));
        userSecurity.setPasswordExpiration(LocalDateTime.now().plusYears(1));

        SecurityCode securityCode = new SecurityCode();
        securityCode.setCode(RandomString.make(64));
        securityCode.setExpiration(LocalDateTime.now().plusWeeks(1));
        userSecurity.setEmailVerificationCode(securityCode);

        userSecurity.setUserName(userName);
        userSecurity.setPassword(passwordEncoder.encode(password));
        return userSecurity;
    }

    private void sendEmailVerification(String email, String code) {
        log.info("Sending Email To: " + email + " with Code: " + code);
    }

    private Role defaultRole() {
        Optional<Role> role = roleRepository.findByName("Consumer");
        return role.orElse(null);
    }

    @Override
    public UserReadDto currentUserDto() {
        UserSecurity userSecurity = (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapper.map(userSecurity.getUser(), UserReadDto.class);
    }

    @Override
    public User currentUser() {
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userObject != null) {
            UserSecurity userSecurity = (UserSecurity)userObject;
            return userSecurity.getUser();
        }
        return null;
    }

    @Override
    public boolean changePassword(String currentPassword, String newPassword) {
        User user = currentUser();
        if(passwordEncoder.matches(currentPassword, user.getUserSecurity().getPassword())) {
            user.getUserSecurity().setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean forgotPassword(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            SecurityCode securityCode = new SecurityCode();
            securityCode.setCode(RandomString.make(64));
            securityCode.setExpiration(LocalDateTime.now().plusWeeks(1));
            user.getUserSecurity().setPasswordResetCode(securityCode);
            sendEmailReset(user.getEmail(), securityCode.getCode());
            return true;
        }
        return false;
    }

    @Override
    public boolean resetPassword(String code, String newPassword) {
        UserSecurity userSecurity = userSecurityRepository.findByResetCode(code).orElse(null);
        if(userSecurity == null) {
            return false;
        }
        SecurityCode securityCode = userSecurity.getPasswordResetCode();
        if(securityCode.getExpiration().isAfter(LocalDateTime.now())) {
            userSecurity.setPassword(passwordEncoder.encode(newPassword));
            return true;
        }
        return false;
    }

    private void sendEmailReset(String email, String code) {
        log.info("Sending Email To: " + email + " with Code: " + code);
    }

    @Override
    public boolean verifyEmail(String verificationCode) {
        User user = currentUser();
        SecurityCode securityCode = user.getUserSecurity().getEmailVerificationCode();
        if(securityCode.getCode() == verificationCode) {
            if(securityCode.getExpiration().isAfter(LocalDateTime.now())) {
                user.getUserSecurity().setVerified(true);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean setUserEnable(Long userId, boolean enable) {
        return false;
    }

    @Override
    public boolean setUserLock(Long userId, boolean lock) {
        return false;
    }

    @Override
    public void setUserExpiration(LocalDateTime expiration) {

    }

    @Override
    public void setPasswordExpiration(LocalDateTime expiration) {

    }

    @Override
    public List<RoleDto> getUserRoles(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return Collections.emptyList();
        }
        return user.getUserSecurity().getRoles().stream().map(role -> mapper.map(role, RoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ScopeDto> getUserScopes(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return Collections.emptyList();
        }
        return user.getUserSecurity().getScopes().stream().map(scope -> mapper.map(scope, ScopeDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ScopeDto> getUserAuthorizedScopes(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return Collections.emptyList();
        }
        List<Role> roles = user.getUserSecurity().getRoles();
        List<Scope> authorizedScopes = new ArrayList<>(user.getUserSecurity().getScopes());
        for(Role role: roles) {
            authorizedScopes.addAll(role.getScopes());
        }
        return authorizedScopes.stream().map(scope -> mapper.map(scope, ScopeDto.class)).collect(Collectors.toList());
    }

    @Override
    public boolean addRoleToUser(Long userId, Long roleId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalUser.isEmpty() || optionalRole.isEmpty()) {
            //TODO: add exception
            return false;
        }
        User user = optionalUser.get();
        user.getUserSecurity().addRole(optionalRole.get());
        return true;
    }

    @Override
    public boolean addScopeToUser(Long userId, Long scopeId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Scope> optionalScope = scopeRepository.findById(scopeId);
        if(optionalUser.isEmpty() || optionalScope.isEmpty()) {
            //TODO: add exception
            return false;
        }
        User user = optionalUser.get();
        user.getUserSecurity().addScope(optionalScope.get());
        return true;
    }

    @Override
    public boolean removeRoleFromUser(Long userId, Long roleId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalUser.isEmpty() || optionalRole.isEmpty()) {
            //TODO: add exception
            return false;
        }
        User user = optionalUser.get();
        Role role = optionalRole.get();
        if(user.getUserSecurity().containsRole(role)) {
            user.getUserSecurity().removeRole(optionalRole.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean removeScopeFromUser(Long userId, Long scopeId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Scope> optionalScope = scopeRepository.findById(scopeId);
        if(optionalUser.isEmpty() || optionalScope.isEmpty()) {
            //TODO: add exception
            return false;
        }
        User user = optionalUser.get();
        Scope scope = optionalScope.get();
        if(user.getUserSecurity().containsScope(scope)) {
            user.getUserSecurity().removeScope(optionalScope.get());
            return true;
        }
        return false;
    }
}
