package com.tim.scientific.portal.back.db.repository;

import com.tim.scientific.portal.back.db.models.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PagesRepository extends CrudRepository<Page, UUID> {

    List<Page> findAll();

    List<Page> findAllByPageType_TypeValue(String pageType);

    @Query(value = "select* " +
            "from pages " +
            "where page_type_id = (select page_type_id from page_type where type_value = :pageType) " +
            "  and by_page_data ->> :key = :value",
            nativeQuery = true)
    List<Page> findByTypeAndMetaData(@Param("pageType") String pageType, @Param("key") String key, @Param("value") String value);

    List<Page> findAllByPageId(UUID pageId);
}
