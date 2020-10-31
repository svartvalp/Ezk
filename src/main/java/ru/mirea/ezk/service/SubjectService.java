package ru.mirea.ezk.service;

import ru.mirea.ezk.dto.SubjectDto;
import ru.mirea.ezk.entity.Subject;

import java.util.List;

public interface SubjectService {
    void createSubject(SubjectDto subjectDto);
    List<Subject> getAllSubjectsByGroupId(String groupId);
    void deleteSubject(String subjectId);
}
