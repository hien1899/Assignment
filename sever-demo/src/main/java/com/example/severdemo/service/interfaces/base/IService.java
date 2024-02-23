package com.example.severdemo.service.interfaces.base;

import java.util.List;
import java.util.Optional;

public interface IService<Dto, Id> {

    Optional<Dto> findById(Id id);

    List<Dto> findAll();

    void delete(Dto t);

    Dto save(Dto t);

    List<Dto> saveAll(Iterable<Dto> ts);
}
