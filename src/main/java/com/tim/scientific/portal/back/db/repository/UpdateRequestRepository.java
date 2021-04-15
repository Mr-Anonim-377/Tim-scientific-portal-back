package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.security.UpdateRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UpdateRequestRepository extends CrudRepository<UpdateRequest, UUID> {

    UpdateRequest findByCod(String cod);

}
