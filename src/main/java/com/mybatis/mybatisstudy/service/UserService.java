package com.mybatis.mybatisstudy.service;

import com.mybatis.mybatisstudy.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> listAllUsers();

    User getUserById(Long id);

    void insertBatch(List<User> userList);

    void insertForEach(List<User> userList);

    void listUserByCursor();

}
