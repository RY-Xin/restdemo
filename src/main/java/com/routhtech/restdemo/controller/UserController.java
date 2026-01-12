package com.routhtech.restdemo.controller;

import com.routhtech.restdemo.entity.User;
import com.routhtech.restdemo.exception.ApiError;
import com.routhtech.restdemo.exception.UserNotFoundException;
import com.routhtech.restdemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String test() {
        return "success";
    }

    @GetMapping
    public List<User> getAll(@RequestParam(required = false, defaultValue = "salary") String sort) {
        List<User> users = service.getAll();
        if (sort.equals("salary")) {
            Collections.sort(users, (u1, u2) -> Double.compare(u1.getSalary(), u2.getSalary()));
        } else if (sort.equals("age")) {
            Collections.sort(users, (u1, u2) -> Double.compare(u1.getAge(), u2.getAge()));
        }
        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        User user = service.getById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @PostMapping
//    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        User created = service.create(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody User user) {
        return   service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ApiError> exceptionHandlerUserNotFound(Exception ex) {
//        ApiError error = new ApiError(
//                Instant.now(),
//                HttpStatus.NOT_FOUND.value(),
//                ex.getMessage()
//        );
//                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }

}
