package com.qf.service;

import com.qf.pojo.User;

public interface UserService {
    User findByEmail(String email);

    int update(User user);

    User findById(Integer id);

    void updateImgUrl(Integer id, String originalFilename);
}
