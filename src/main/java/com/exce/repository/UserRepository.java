package com.exce.repository;

import com.exce.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, BigInteger> {

    Optional<User> findOneByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findById(BigInteger userId);
    User findUserById(BigInteger userId);
}
