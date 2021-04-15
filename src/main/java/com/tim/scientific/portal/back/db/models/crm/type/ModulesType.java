package com.tim.scientific.portal.back.db.models.crm.type;

import com.tim.scientific.portal.back.db.models.Module;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules_type")
@Getter
@Setter
public class ModulesType {

    @Id
    @GeneratedValue()
    private Integer moduleTypeId;

    private String typeValue;

    @OneToMany(mappedBy = "moduleType")
    private List<Module> modules = new ArrayList<>();

}