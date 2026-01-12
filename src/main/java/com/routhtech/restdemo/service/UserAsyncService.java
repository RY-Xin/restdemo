package com.routhtech.restdemo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserAsyncService {
    @Async
    public void asyncLog(Long id) {
        System.out.println("Async task for user " + id);
    }
}
