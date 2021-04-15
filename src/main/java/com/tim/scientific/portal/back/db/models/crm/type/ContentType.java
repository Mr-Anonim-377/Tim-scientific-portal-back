package com.tim.scientific.portal.back.db.models.crm.type;

import com.tim.scientific.portal.back.db.models.Content;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contents_type")
@Getter
@Setter
public class ContentType {

    @Id
    @GeneratedValue()
    private Integer contentTypeId;

    private String typeValue;

    @OneToMany(mappedBy = "contentType")
    private List<Content> contents = new ArrayList<>();

}