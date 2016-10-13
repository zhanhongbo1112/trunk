package com.yqboots.project.security.access.support;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityGenerator;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;

import java.io.Serializable;
import java.util.List;

/**
 * Delegates the retrieval strategy to others.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class DelegatingObjectIdentityRetrievalStrategy implements ObjectIdentityRetrievalStrategy, ObjectIdentityGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(DelegatingObjectIdentityRetrievalStrategy.class);

    private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();
    private ObjectIdentityGenerator objectIdentityGenerator = new ObjectIdentityRetrievalStrategyImpl();

    private List<ObjectIdentityRetrieval> objectIdentityRetrievals;

    public DelegatingObjectIdentityRetrievalStrategy(final List<ObjectIdentityRetrieval> objectIdentityRetrievals) {
        this.objectIdentityRetrievals = objectIdentityRetrievals;
    }

    @Override
    public ObjectIdentity getObjectIdentity(final Object domainObject) {
        ObjectIdentity result = null;
        if (CollectionUtils.isNotEmpty(objectIdentityRetrievals)) {
            for (ObjectIdentityRetrieval retrieval : objectIdentityRetrievals) {
                if (retrieval.supports(domainObject.getClass())) {
                    result = retrieval.retrieve(domainObject);
                    break;
                }
            }
        } else {
            result = objectIdentityRetrievalStrategy.getObjectIdentity(domainObject);
        }

        return result;
    }

    @Override
    public ObjectIdentity createObjectIdentity(final Serializable id, final String type) {
        ObjectIdentity result = null;
        if (CollectionUtils.isNotEmpty(objectIdentityRetrievals)) {
            try {
                Class<?> clazz = Class.forName(type);
                for (ObjectIdentityRetrieval retrieval : objectIdentityRetrievals) {
                    if (retrieval.supports(clazz)) {
                        result = retrieval.retrieve(id);
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                LOG.error(type, e);
            }
        } else {
            result = objectIdentityGenerator.createObjectIdentity(id, type);
        }

        return result;
    }

    public void setObjectIdentityRetrievalStrategy(final ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy) {
        this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
    }

    public void setObjectIdentityGenerator(final ObjectIdentityGenerator objectIdentityGenerator) {
        this.objectIdentityGenerator = objectIdentityGenerator;
    }
}
