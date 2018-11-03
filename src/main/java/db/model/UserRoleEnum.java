package db.model;

public enum UserRoleEnum {

    ADMIN("ADMIN"), USER("USER");


    private final String roleName;

    UserRoleEnum(String name) {
        this.roleName = name;
    }

    @Override
    public String toString() {
        return roleName;
    }

}
