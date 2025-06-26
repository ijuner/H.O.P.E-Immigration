package ca.conestoga.project.util;

import java.util.Arrays;

/**
 * enumeration for privilege
 */
public enum PrivilegeEnum {
    VIEW_ADMIN("View Admin", "view_admin"),
    ADD_ADMIN("Add Admin", "add_admin"),
    EDIT_ADMIN("Edit Admin", "edit_admin"),
    EDIT_ADMIN_STATUS("Edit Admin Status", "edit_admin_status"),
    VIEW_ROLE("View Role", "view_role"),
    EDIT_ROLE("Edit Role", "edit_role"),
    DELETE_ROLE("Delete Role", "delete_role"),
    RESET_PASSWORD("Reset Password", "reset_password"),
    ADMIN_BIND_ROLE("Bind Role", "admin_bind_role"),
    ROLE_BIND_PRIVILEGE("Bind Privilege", "role_bind_privilege");

    private final String name;
    private final String value;

    PrivilegeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "PrivilegeEnum{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static PrivilegeEnum findPrivilegeByValue(String value) {
        return Arrays.stream(PrivilegeEnum.values()).filter(privilegeEnum -> privilegeEnum.getValue().equals(value)).findFirst().orElse(null);
    }
}
