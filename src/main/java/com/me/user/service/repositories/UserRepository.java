package com.me.user.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.user.service.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

}
