package ru.mirea.ezk.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.mirea.ezk.entity.Group;

public interface GroupDao extends MongoRepository<Group, String> {
    Group findByGroupName(String groupName);
    @Query(value = "{ \"students.userId\" : ?0 }}")
    Group findByStudentInfoUserId(String userId);
}
