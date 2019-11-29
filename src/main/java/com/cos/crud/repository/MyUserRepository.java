package com.cos.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.crud.model.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Integer>{

	MyUser findByUsername(String username);
}
