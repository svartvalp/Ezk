package ru.mirea.ezk.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.mirea.ezk.dao.GroupDao;
import ru.mirea.ezk.dao.StudentSubjectDao;
import ru.mirea.ezk.dao.SubjectDao;
import ru.mirea.ezk.dto.SubjectDto;
import ru.mirea.ezk.entity.Group;
import ru.mirea.ezk.entity.StudentSubject;
import ru.mirea.ezk.entity.Subject;
import ru.mirea.ezk.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectDao subjectDao;
    private final ModelMapper modelMapper;
    private final GroupDao groupDao;
    private final StudentSubjectDao studentSubjectDao;


    @Override
    public void createSubject(SubjectDto subjectDto) {
        Subject subject = modelMapper.map(subjectDto, Subject.class);
        Optional<Group> groupOptional = groupDao.findById(subject.getGroupId());
        if(groupOptional.isPresent()) {
            Group group = groupOptional.get();
            subject = subjectDao.save(subject);
            group.getSubjects().add(subject);
            group = groupDao.save(group);
            if(group.getStudents() != null && !group.getStudents().isEmpty()) {
                Subject finalSubject = subject;
                Group finalGroup = group;
                group.getStudents().forEach(studentInfo -> {
                    StudentSubject studentSubject = new StudentSubject();
                    studentSubject.setGroupId(finalGroup.getId());
                    studentSubject.setGroupName(finalSubject.getGroupName());
                    studentSubject.setTeacherId(finalSubject.getTeacherId());
                    studentSubject.setTeacherFirstName(finalSubject.getTeacherFirstName());
                    studentSubject.setTeacherLastName(finalSubject.getTeacherLastName());
                    studentSubject.setStudentFirstName(studentInfo.getFirstName());
                    studentSubject.setStudentLastName(studentInfo.getLastName());
                    studentSubject.setSubjectId(finalSubject.getId());
                    studentSubject.setSubjectName(finalSubject.getName());
                    studentSubject.setStudentId(studentInfo.getUserId());
                    studentSubjectDao.save(studentSubject);
                });
            }
        } else {
            throw new EntityNotFoundException("group");
        }
    }

    @Override
    public List<Subject> getAllSubjectsByGroupId(String groupId) {
        Optional<Group> groupOptional = groupDao.findById(groupId);
        Group group = groupOptional.orElseThrow(() -> new EntityNotFoundException("Group"));
        return group.getSubjects();
    }

    @Override
    public void deleteSubject(String subjectId) {
        if(subjectDao.existsById(subjectId)) {
            subjectDao.deleteById(subjectId);
            studentSubjectDao.deleteAllBySubjectId(subjectId);
        } else {
            throw new EntityNotFoundException("Subject");
        }
    }
}
