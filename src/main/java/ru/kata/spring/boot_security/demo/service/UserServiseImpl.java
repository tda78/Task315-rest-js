package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiseImpl implements UserService {

    private UserDao dao;

    private PasswordEncoder encoder;

    @Autowired
    public UserServiseImpl(UserDao dao, PasswordEncoder encoder) {
        this.dao = dao;
        this.encoder = encoder;
    }


    @Override
    public List<User> getAllUsers() {
        return (List<User>) dao.findAll();
    }

    @Override
    public User getUser(Long id) {
        return dao.findById(id).get();
    }


    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        dao.save(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        dao.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        dao.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.getUserByName(username);
        return user;
    }
}
