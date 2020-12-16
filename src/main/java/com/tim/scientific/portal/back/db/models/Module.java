package com.tim.scientific.portal.back.db.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "modules")
@Getter
@Setter
public class Module {

    @Id
    @GeneratedValue()
    private UUID moduleId;

    private Integer rank;

    private String name;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn (name="module_type_id")
    private ModulesType moduleType;

    private Integer objectCount;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "module")
    private List<ModulesObject> modulesObjects = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "page_id")
    private com.tim.scientific.portal.back.db.models.Page page;
}