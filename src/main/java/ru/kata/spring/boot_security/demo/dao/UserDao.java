package ru.kata.spring.boot_security.demo.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    @Query("from User u join fetch u.roles where u.email = :email")
    User getUserByName(@Param("email") String email);
}
