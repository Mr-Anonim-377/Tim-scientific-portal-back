package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends CrudRepository<User, UUID> {

    User findByLogin(String login);

}
