package com.tim.scientific.portal.back.db.models.security;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role implements GrantedAuthority {
    @Id
    private UUID id;

    private String name;

    @Type(type = "jsonb-node")
    @Column(columnDefinition = "jsonb")
    private JsonNode editingEntity;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(UUID id) {
        this.id = id;
    }

    public Role(UUID id, String name) {
        this.id = id;
        this.name = "ROLE_" + name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
