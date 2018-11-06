package com.travel.book.easy.travelbookeasy.db.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserRolePk id;

    public UserRolePk getId() {
        return this.id;
    }

    public void setId(UserRolePk id) {
        this.id = id;
    }
}
