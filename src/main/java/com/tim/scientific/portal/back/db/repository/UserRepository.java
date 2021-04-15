package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    User findByUsername(String username);

    List<User> findAll();

    User findUserByEmail(String email);
}