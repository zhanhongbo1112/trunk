package com.yqsoftwares.security.access;

import com.yqsoftwares.security.core.repository.RoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2015-12-14.
 */
public class RoleHierarchyImpl implements RoleHierarchy {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Assert.notEmpty(authorities);

        Set<String> roles = new HashSet<>();
        for (GrantedAuthority role : authorities) {
            roles.addAll(retrieveHierarchyRoles(role));
        }

        return roleRepository.findByPathIn(roles);
    }

    protected Collection<? extends String> retrieveHierarchyRoles(GrantedAuthority role) {
        Set<String> result = new HashSet<>();
        // /R001/R002/R003 ...
        String roleStr = role.getAuthority();

        while (StringUtils.isNotBlank(roleStr)) {
            result.add(roleStr);
            roleStr = StringUtils.substringBeforeLast(roleStr, "/");
        }

        return result;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
