package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.security.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {

    Role findByName(String name);

}