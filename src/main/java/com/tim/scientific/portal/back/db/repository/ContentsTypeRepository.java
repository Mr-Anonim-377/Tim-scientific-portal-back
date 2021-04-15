package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.crm.type.ContentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentsTypeRepository extends CrudRepository<ContentType, Integer> {

    List<ContentType> findAll();
        }
