package com.tim.scientific.portal.back.db.models.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.tim.scientific.portal.back.db.models.Page;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_page")
@Getter
@Setter
public class UserPage {

    @Id
    @GeneratedValue()
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "page_id")
    private Page page;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}