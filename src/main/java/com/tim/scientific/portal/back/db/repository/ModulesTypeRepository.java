package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.ModulesType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModulesTypeRepository extends CrudRepository<ModulesType, Integer> {

    List<ModulesType> findAll();

}
