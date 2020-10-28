package com.tim.scientific.portal.back.db.models;

import com.tim.scientific.portal.back.dto.PageTypeEnum;
import lombok.Getter;
import lombok.Setter;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "page_type")
    private PageTypeEnum pageType;

    private Boolean active;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "page")
    private List<Module> modules = new ArrayList<>();
}