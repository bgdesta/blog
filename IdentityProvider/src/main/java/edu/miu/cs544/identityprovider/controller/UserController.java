package edu.miu.cs544.identityprovider.controller;

import edu.miu.cs544.identityprovider.dto.UserCreateDto;
import edu.miu.cs544.identityprovider.dto.UserReadDto;
import edu.miu.cs544.identityprovider.dto.UserUpdateDto;
import edu.miu.cs544.identityprovider.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService UserService;

    @Autowired
    public UserController(UserService rs) {
        UserService = rs;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserReadDto>> getAll() {
        return ResponseEntity.ok(UserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReadDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(UserService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<UserReadDto> create(@RequestBody UserCreateDto UserDto) {
        log.info("User dto received: " + UserDto);
        return ResponseEntity.ok(UserService.save(UserDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UserUpdateDto UserDto) {
        UserService.update(id, UserDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        UserService.delete(id);
        return ResponseEntity.ok().build();
    }
}
