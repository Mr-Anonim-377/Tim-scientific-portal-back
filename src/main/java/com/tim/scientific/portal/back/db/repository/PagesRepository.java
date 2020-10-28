package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.Page;
import com.tim.scientific.portal.back.dto.PageTypeEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PagesRepository extends CrudRepository<Page, UUID> {
        List<Page> findAllByPageType(PageTypeEnum pageType);

        List<Page> findAllByPageId(UUID pageId);
}
