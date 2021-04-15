package com.tim.scientific.portal.back.db.models.crm.type;

import com.tim.scientific.portal.back.db.models.Page;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "page_type")
@Getter
@Setter
public class PageType {

    @Id
    @GeneratedValue()
    private Integer pageTypeId;

    private String typeValue;

    @OneToMany(mappedBy = "pageType")
    private List<Page> pages = new ArrayList<>();

}