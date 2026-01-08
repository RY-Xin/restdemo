package com.routhtech.restdemo.service;

import com.routhtech.restdemo.entity.User;
import com.routhtech.restdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User create(User user) {
        return userRepo.save(user);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public User update(Long id, User user) {
        User existing = getById(id);
        existing.setName(user.getName());
        existing.setAge(user.getAge());
        existing.setSalary(user.getSalary());
        return userRepo.save(existing);
    }

    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        userRepo.deleteById(id);
    }

}
