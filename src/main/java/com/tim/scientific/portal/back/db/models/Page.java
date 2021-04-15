package com.tim.scientific.portal.back.db.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.tim.scientific.portal.back.db.models.crm.type.PageType;
import com.tim.scientific.portal.back.db.models.security.UserPage;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pages")
@Getter
@Setter
public class Page {

    @Id
    @GeneratedValue()
    private UUID pageId;

    private String name;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "page_type_id")
    private PageType pageType;

    private Boolean active;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "page")
    private List<Module> modules = new ArrayList<>();

    @Type(type = "jsonb-node")
    @Column(columnDefinition = "jsonb")
    private JsonNode byPageData;

    @OneToMany(mappedBy = "page")
    private List<UserPage> editingUser  = new ArrayList<>();
}