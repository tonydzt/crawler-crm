package com.kasuo.crawler.domain.permission;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.PermissionsResourceType;

import java.util.HashSet;
import java.util.Set;

public class PermissionResource extends GenericEntity {
    private static final long serialVersionUID = 6566288140120438450L;
    private String resourceContent;
    private PermissionResource parent;
    private Integer level;
    private Integer sequence;
    private String parameter;
    private PermissionsResourceType type;
    private Set<Role> roles = new HashSet();

    private Set<PermissionResource> children = new HashSet();

    public PermissionResource() {
    }

    public PermissionResource(String id) {
        setId(id);
    }

    public String getResourceContent() {
        return resourceContent;
    }

    public void setResourceContent(String resourceContent) {
        this.resourceContent = resourceContent;
    }

    public PermissionResource getParent() {
        return parent;
    }

    public void setParent(PermissionResource parent) {
        this.parent = parent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public PermissionsResourceType getType() {
        return type;
    }

    public void setType(PermissionsResourceType type) {
        this.type = type;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<PermissionResource> getChildren() {
        return children;
    }

    public void setChildren(Set<PermissionResource> children) {
        this.children = children;
    }
}
