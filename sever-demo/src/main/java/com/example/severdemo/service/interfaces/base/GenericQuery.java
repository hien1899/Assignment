package com.example.severdemo.service.interfaces.base;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public interface GenericQuery<T> {

    default Path<T> getPath(Root<T> root, String[] properties) {
        Path<T> path = properties.length == 0 ? null : root.get(properties[0]);
        for (int i = 1; i < properties.length; i++) {
            path = path.get(properties[i]);
        }
        return path;
    }

    default Specification<T> getQueryForNotNull(String... properties) {
        return (root, query, cb) -> cb.isNotNull(getPath(root, properties));
    }

    default Specification<T> getQueryForEqual(Object value, String... properties) {
        return (root, query, cb) -> cb.equal(getPath(root, properties), value);
    }

    default Specification<T> getQueryForIn(List in, String... properties) {
        return (root, query, cb) -> getPath(root, properties).in(in);
    }

}
