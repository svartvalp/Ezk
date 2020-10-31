package ru.mirea.ezk.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mirea.ezk.entity.StudentInfo;

public interface StudentInfoDao extends MongoRepository<StudentInfo, String> {
}
