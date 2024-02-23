package com.example.severdemo.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, Id> extends JpaRepository<T, Id>, JpaSpecificationExecutor<T> {
}

