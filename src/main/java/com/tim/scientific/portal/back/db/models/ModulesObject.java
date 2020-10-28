package com.tim.scientific.portal.back.db.models;

import com.tim.scientific.portal.back.dto.ModulesObjectTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "modules_objects")
public class ModulesObject {

    @Id
    @GeneratedValue()
    private UUID modulesObjectsId;

    private String name;

    private Integer objectRank;

    @Enumerated(EnumType.STRING)
    @Column(name = "module_object_type")
    private ModulesObjectTypeEnum modulesObjectType;

    @OneToMany(mappedBy = "modulesObject")
    private List<Content> contents = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "module_id")
    private Module module;
}
