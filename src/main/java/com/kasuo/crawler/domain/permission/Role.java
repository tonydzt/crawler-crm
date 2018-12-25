package com.kasuo.crawler.domain.permission;

import com.kasuo.crawler.domain.core.GenericEntity;

import java.util.HashSet;
import java.util.Set;


public class Role extends GenericEntity {
    private static final long serialVersionUID = 1L;
    public static final String ALL_ROLE_ID = "4028db815fbb11de015fbd56af120004";

    public Role() {
    }

    public boolean isAll() {
        if (id.equals("4028db815fbb11de015fbd56af120004")) {
            return true;
        }
        return false;
    }

    private Set<User> users = new HashSet();

    private Set<PermissionResource> permissionResources = new HashSet();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<PermissionResource> getPermissionResources() {
        return permissionResources;
    }

    public void setPermissionResources(Set<PermissionResource> permissionResources) {
        this.permissionResources = permissionResources;
    }
}
