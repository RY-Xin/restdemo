package com.routhtech.restdemo.service;

import lombok.extern.slf4j.Slf4j;
import com.routhtech.restdemo.entity.User;
import com.routhtech.restdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    private UserAsyncService asyncService;


    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED
    )

    public User create(User user) {
        log.info("Creating user, name={}, age={}, salary={}",
                user.getName(), user.getAge(), user.getSalary());
        User saved = userRepo.save(user);
        asyncService.asyncLog(saved.getId());

        return saved;
    }

    public List<User> getAll() {
        log.debug("Fetching all users");
        return userRepo.findAll();
    }

    @Cacheable("users")
    public User getById(Long id) {

        log.info("Fetching user by id={}", id);

        return userRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found, id={}", id);
                    return new RuntimeException("User not found: " + id);
                });
    }

    @Transactional
    public User update(Long id, User user) {
        log.info("Updating user id={}", id);

        User existing = getById(id);

        existing.setName(user.getName());
        existing.setAge(user.getAge());
        existing.setSalary(user.getSalary());

        return userRepo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting user id={}", id);

        if (!userRepo.existsById(id)) {
            log.warn("Cannot delete, user not found, id={}", id);
            throw new RuntimeException("User not found: " + id);
        }

        userRepo.deleteById(id);
    }

}
