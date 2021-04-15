package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.crm.type.PageType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageTypeRepository extends CrudRepository<PageType, Integer> {

    List<PageType> findAll();
}
