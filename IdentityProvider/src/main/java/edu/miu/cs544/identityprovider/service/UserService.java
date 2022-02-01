package edu.miu.cs544.identityprovider.service;

import edu.miu.cs544.identityprovider.dto.UserCreateDto;
import edu.miu.cs544.identityprovider.dto.UserReadDto;
import edu.miu.cs544.identityprovider.dto.UserUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserReadDto> findAll();
    List<UserReadDto> findAll(Pageable pageable);
    UserReadDto findById(Long id);
    void update(Long id, UserUpdateDto userDto);
    void delete(Long id);
}
