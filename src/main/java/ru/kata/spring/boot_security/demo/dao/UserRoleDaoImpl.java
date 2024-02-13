package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class UserRoleDaoImpl implements UserRoleDao{
    @PersistenceContext
    EntityManager em;
    @Override
    public User getUserByName(String email) {
        Query query = em.createQuery("from User u join fetch u.roles where u.email = :email");
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }
}
