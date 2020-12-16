package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.ModulesObjectType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModulesObjectTypeRepository extends CrudRepository<ModulesObjectType, Integer> {

    List<ModulesObjectType> findAll();
}
