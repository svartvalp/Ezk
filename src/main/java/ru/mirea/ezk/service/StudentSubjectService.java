package ru.mirea.ezk.service;

import ru.mirea.ezk.entity.StudentSubject;

import java.util.List;

public interface StudentSubjectService {
    List<StudentSubject> getStudentSubjectsByGroupId(String groupId);
    List<StudentSubject> getSubjectsByTeacherId(String teacherId);
    List<StudentSubject> getSubjectsByStudentId(String studentId);
    List<StudentSubject> getSubjectsByGroupIdAndSubjectId(String groupId, String subjectId);
    void setMark(String subjectId, String mark);
}
