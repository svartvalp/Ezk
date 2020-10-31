package ru.mirea.ezk.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mirea.ezk.entity.StudentSubject;

import java.util.List;

public interface StudentSubjectDao extends MongoRepository<StudentSubject, String> {
    List<StudentSubject> findByGroupId(String groupId);
    List<StudentSubject> findByTeacherId(String teacherId);
    List<StudentSubject> findByStudentId(String studentId);
    List<StudentSubject> findByGroupIdAndSubjectId(String groupId, String subjectId);
    void deleteAllBySubjectId(String subjectId);
    void deleteAllByStudentId(String studentId);
}
