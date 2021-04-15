package com.tim.scientific.portal.back.db.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "modules_objects_tag")
public class ModuleObjectTag {

    @Id
    @GeneratedValue()
    private UUID modulesObjectsTagId;

    private String tag;

}