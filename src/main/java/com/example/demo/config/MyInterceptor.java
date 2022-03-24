package com.example.demo.config;

import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyInterceptor extends EmptyInterceptor {
    private final Logger logger = Logger.getLogger(MyInterceptor.class);

    private final Set<Object> inserts = new HashSet<>();

    public boolean onSave(Object entity, Serializable id, Object[] state,
                          String[] propertyNames, Type[] types)
            throws CallbackException {
        inserts.add(entity);
        return false;
    }

    public void postFlush(Iterator iterator) throws CallbackException {
        for (Object object : inserts) {
            logger.info("[INTERCEPTOR] Object created : " + object);
        }
    }
}
