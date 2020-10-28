package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.Module;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModulesRepository extends CrudRepository<Module, UUID> {

        List<Module> findAllByPage_PageId(UUID pageId);

        Module findByModuleId(UUID moduleId);
}
