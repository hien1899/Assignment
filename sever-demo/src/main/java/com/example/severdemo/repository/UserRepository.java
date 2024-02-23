package com.example.severdemo.repository;

import com.example.severdemo.entity.User;
import com.example.severdemo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

    @Query(value = "select u from User u where (u.fName like :name) or (u.lName like :name)")
    List<User> findUserByAllName(@Param("name") String name);

    @Query(value = "select u from User u where u.fName like ?1")
    List<User> findUserByFName(String name);

    @Query(value = "select u from User u where u.lName like ?1")
    List<User> findUserByLName(String name);

    @Query(value = "select u from User u where u.dob between ?1 and ?2")
    List<User> findUserByDob(Instant beginDate, Instant endDate);
}
