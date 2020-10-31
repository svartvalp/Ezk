package ru.mirea.ezk.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mirea.ezk.entity.User;

import java.util.List;

public interface UserDao extends MongoRepository<User, String> {
    public User findByLogin(String login);
    public List<User> findByRolesContains(String role);
}
