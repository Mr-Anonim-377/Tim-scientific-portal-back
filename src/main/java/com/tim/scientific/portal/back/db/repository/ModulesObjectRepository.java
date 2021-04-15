package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.ModulesObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModulesObjectRepository extends CrudRepository<ModulesObject, UUID> {

    ModulesObject findByModulesObjectsId(UUID modulesObjectsId);

    List<ModulesObject> findByModule_ModuleId_AndTag_Tag(UUID modulesObjectsId, String tag_tag);
}
