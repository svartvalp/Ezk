package ru.mirea.ezk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ezk.dao.StudentSubjectDao;
import ru.mirea.ezk.entity.StudentSubject;
import ru.mirea.ezk.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentSubjectServiceImpl implements StudentSubjectService {
    private final StudentSubjectDao studentSubjectDao;

    @Override
    public List<StudentSubject> getStudentSubjectsByGroupId(String groupId) {
        return studentSubjectDao.findByGroupId(groupId);
    }

    @Override
    public List<StudentSubject> getSubjectsByTeacherId(String teacherId) {
        return studentSubjectDao.findByTeacherId(teacherId);
    }

    @Override
    public List<StudentSubject> getSubjectsByGroupIdAndSubjectId(String groupId, String subjectId) {
        return studentSubjectDao.findByGroupIdAndSubjectId(groupId, subjectId);
    }

    @Override
    public void setMark(String subjectId, String mark) {
        Optional<StudentSubject> studentSubjectOptional = studentSubjectDao.findById(subjectId);
        if(studentSubjectOptional.isEmpty()) {
            throw new EntityNotFoundException("Subject");
        }
        StudentSubject studentSubject = studentSubjectOptional.get();
        studentSubject.setMark(mark);
        studentSubjectDao.save(studentSubject);
    }
}
