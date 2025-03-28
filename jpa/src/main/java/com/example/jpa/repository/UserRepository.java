
package com.example.jpa.repository;

import com.example.jpa.model.AppUser;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {

    // 1. Basic SELECT with native query
    @Query(value = "SELECT * FROM app_user WHERE email LIKE %:domain", nativeQuery = true)
    List<AppUser> findByEmailDomain(@Param("domain") String domain);
}