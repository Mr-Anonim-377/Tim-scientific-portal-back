package com.tim.scientific.portal.back.db.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules_objects_type")
@Getter
@Setter
public class ModulesObjectType {

    @Id
    @GeneratedValue()
    private Integer modulesObjectTypeId;

    private String typeValue;

    @OneToMany(mappedBy = "modulesObjectType")
    private List<ModulesObject> modulesObjects = new ArrayList<>();

}