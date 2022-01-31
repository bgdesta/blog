package edu.miu.cs544.identityprovider.service.impl;

import edu.miu.cs544.identityprovider.domain.SecurityCode;
import edu.miu.cs544.identityprovider.domain.User;
import edu.miu.cs544.identityprovider.domain.UserSecurity;
import edu.miu.cs544.identityprovider.dto.UserCreateDto;
import edu.miu.cs544.identityprovider.dto.UserReadDto;
import edu.miu.cs544.identityprovider.dto.UserUpdateDto;
import edu.miu.cs544.identityprovider.repository.UserRepository;
import edu.miu.cs544.identityprovider.service.UserService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserReadDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> mapper.map(user, UserReadDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<UserReadDto> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.stream().map(user -> mapper.map(user, UserReadDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserReadDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return mapper.map(user, UserReadDto.class);
    }

    @Override
    public UserReadDto save(UserCreateDto userDto) {
        User user = mapper.map(userDto, User.class);

        UserSecurity userSecurity = defaultUserSecurity(user.getEmail(), userDto.getPassword());
        user.setUserSecurity(userSecurity);

        userRepository.save(user);
        log.info("New user created: " + user);

        sendEmailVerification(user.getEmail(), userSecurity.getEmailVerificationCode().getCode());
        return mapper.map(user, UserReadDto.class);
    }

    @Override
    public void update(Long id, UserUpdateDto dto) {
        User userDb = userRepository.findById(id).orElseThrow();
        User user = mapper.map(dto, User.class);
        mapper.map(user, userDb);
        userRepository.save(userDb);
        log.info("User updated: " + user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("User deleted id: " + id);
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
}
