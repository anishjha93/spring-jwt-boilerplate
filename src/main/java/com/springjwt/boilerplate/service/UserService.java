package com.springjwt.boilerplate.service;

import java.util.List;

import com.springjwt.boilerplate.model.User;


public interface UserService {
    public User findById(Long id);
    public User findByUsername(String username);
    public List<User> findAll ();
    public User addUser(User user);
}
