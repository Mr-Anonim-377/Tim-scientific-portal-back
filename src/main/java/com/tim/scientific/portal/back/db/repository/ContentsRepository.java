package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.Content;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContentsRepository extends CrudRepository<Content, UUID> {
}
