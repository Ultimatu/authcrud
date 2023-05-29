package com.ultimatum.authcrud.repositories;

import com.ultimatum.authcrud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //find user by email and password
    Optional<User> findByEmailAndPassword(String email, String password);

    //find user by email
    Optional<User> findByEmail(String email);

    //find user by id
    Optional<User> findByPassword(String password);

    boolean existsByEmailAndIdNot(String email, Integer id);
}
