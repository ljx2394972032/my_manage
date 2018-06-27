package com.vigekoo.manage.sys.entity;

import java.io.Serializable;

/**
 * 角色与菜单对应关系
 */
public class SysRoleMenu implements Serializable {

    private Long id;

    private Long roleId;

    private Long menuId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getMenuId() {
        return menuId;
    }

}
