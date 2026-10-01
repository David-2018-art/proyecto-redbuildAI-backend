package com.example.redbuild_ai_backend.dtos;

public class UserCountByRoleDTO {
    private Long idRole;
    private String nameRole;
    private Long quantityUsers;

    public UserCountByRoleDTO() {
    }

    public UserCountByRoleDTO(
            Long idRole,
            String nameRole,
            Long quantityUsers) {

        this.idRole = idRole;
        this.nameRole = nameRole;
        this.quantityUsers = quantityUsers;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public Long getQuantityUsers() {
        return quantityUsers;
    }

    public void setQuantityUsers(Long quantityUsers) {
        this.quantityUsers = quantityUsers;
    }
}
