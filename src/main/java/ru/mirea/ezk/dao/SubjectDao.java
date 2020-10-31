package ru.mirea.ezk.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mirea.ezk.entity.Subject;

public interface SubjectDao extends MongoRepository<Subject, String> {
}
