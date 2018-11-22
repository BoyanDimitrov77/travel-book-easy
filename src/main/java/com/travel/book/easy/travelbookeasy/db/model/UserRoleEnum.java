package com.travel.book.easy.travelbookeasy.db.model;

public enum UserRoleEnum {

    ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");


    private final String roleName;

    UserRoleEnum(String name) {
        this.roleName = name;
    }

    @Override
    public String toString() {
        return roleName;
    }

}
