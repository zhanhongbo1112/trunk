/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.project.security.autoconfigure;

import com.yqboots.project.security.access.RoleHierarchyImpl;
import com.yqboots.project.security.core.repository.RoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.annotation.Jsr250Voter;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Extends {@link GlobalMethodSecurityConfiguration} to provide global method security.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class DefaultMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    @Autowired
    private RoleHierarchy roleHierarchy;

    @Autowired
    private PermissionEvaluator permissionEvaluator;

    /**
     * {@inheritDoc}
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler bean = new DefaultMethodSecurityExpressionHandler();
        bean.setDefaultRolePrefix(StringUtils.EMPTY);
        bean.setPermissionEvaluator(permissionEvaluator);
        bean.setRoleHierarchy(roleHierarchy);

        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();

        ExpressionBasedPreInvocationAdvice expressionAdvice = new ExpressionBasedPreInvocationAdvice();
        expressionAdvice.setExpressionHandler(getExpressionHandler());
        decisionVoters.add(new PreInvocationAuthorizationAdviceVoter(expressionAdvice));

        // decisionVoters.add(new RoleVoter());
        decisionVoters.add(roleVoter());
        decisionVoters.add(new Jsr250Voter());
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(roleHierarchyVoter());
        return new AffirmativeBased(decisionVoters);
    }

    private RoleVoter roleVoter() {
        RoleVoter bean = new RoleVoter();
        bean.setRolePrefix(StringUtils.EMPTY);
        return bean;
    }

    private RoleVoter roleHierarchyVoter() {
        RoleVoter bean = new RoleHierarchyVoter(roleHierarchy);
        bean.setRolePrefix(StringUtils.EMPTY);
        return bean;
    }
}