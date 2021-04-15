package com.tim.scientific.portal.back.db.models.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "update_request")
@Getter
@Setter
public class UpdateRequest {

    @Id
    @GeneratedValue()
    private UUID requestId;

    private String cod;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
