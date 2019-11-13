package com.springjwt.boilerplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springjwt.boilerplate.model.User;

/**
 * Created by fan.jin on 2016-10-15.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
}

