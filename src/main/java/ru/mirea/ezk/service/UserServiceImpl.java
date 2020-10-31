package ru.mirea.ezk.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mirea.ezk.dao.GroupDao;
import ru.mirea.ezk.dao.StudentInfoDao;
import ru.mirea.ezk.dao.StudentSubjectDao;
import ru.mirea.ezk.dao.UserDao;
import ru.mirea.ezk.dto.SecuredUserDto;
import ru.mirea.ezk.dto.StudentDto;
import ru.mirea.ezk.dto.TeacherDto;
import ru.mirea.ezk.dto.UserDto;
import ru.mirea.ezk.entity.Group;
import ru.mirea.ezk.entity.StudentInfo;
import ru.mirea.ezk.entity.StudentSubject;
import ru.mirea.ezk.entity.User;
import ru.mirea.ezk.exception.EntityNotFoundException;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final StudentInfoDao studentInfoDao;
    private final StudentSubjectDao studentSubjectDao;
    private final GroupDao groupDao;
    private final ModelMapper modelMapper;


    @Value("${admin.login}")
    private String adminLogin;
    @Value("${admin.password}")
    private String adminPassword;

    @Override
    @PostConstruct
    public void init() {
        if(userDao.findByLogin(adminLogin) == null) {
            User user = new User();
            user.setLogin(adminLogin);
            user.setPassword(adminPassword);
            user.setRoles(List.of("ADMIN"));
            userDao.save(user);
        }
    }

    @Override
    public void deleteUser(String userId) {
        User user = userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException("User"));
        if(user.getRoles().contains("STUDENT")) {
            Group group = groupDao.findByStudentInfoUserId(userId);
            group.setStudents(group
                    .getStudents()
                    .stream()
                    .filter(studentInfo -> !studentInfo.getUserId().equals(userId))
                    .collect(Collectors.toList()));
            groupDao.save(group);
            studentSubjectDao.deleteAllByStudentId(userId);
        }
        userDao.deleteById(userId);
    }

    @Override
    public SecuredUserDto getUserInfo(Principal principal) {
        User user = userDao.findByLogin(principal.getName());
        return modelMapper.map(user, SecuredUserDto.class);
    }

    @Override
    public StudentInfo getStudentInfoByUserId(String userId) {
        Group group = groupDao.findByStudentInfoUserId(userId);
        return group
                .getStudents()
                .stream()
                .filter(student -> student.getUserId().equals(userId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Student info"));
    }

    @Override
    public void createStudent(String groupId, StudentDto studentDto) {
        Optional<Group> groupOptional = groupDao.findById(groupId);
        if(groupOptional.isPresent()) {
            Group group = groupOptional.get();
            User user = new User();
            user.setLogin(studentDto.getLogin());
            user.setPassword(studentDto.getPassword());
            user.setFirstName(studentDto.getFirstName());
            user.setLastName(studentDto.getLastName());
            user.setRoles(List.of("STUDENT"));
            user = userDao.save(user);

            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setFirstName(studentDto.getFirstName());
            studentInfo.setLastName(studentDto.getLastName());
            studentInfo.setGroupId(groupId);
            studentInfo.setGroupName(group.getGroupName());
            studentInfo.setUserId(user.getId());
            studentInfo = studentInfoDao.save(studentInfo);
            group.getStudents().add(studentInfo);
            group = groupDao.save(group);

            if(group.getSubjects() != null && !group.getSubjects().isEmpty()) {
                User finalUser = user;
                Group finalGroup = group;
                group.getSubjects().forEach(subject -> {
                    StudentSubject studentSubject = new StudentSubject();
                    studentSubject.setGroupId(finalGroup.getId());
                    studentSubject.setStudentId(finalUser.getId());
                    studentSubject.setStudentFirstName(finalUser.getFirstName());
                    studentSubject.setStudentLastName(finalUser.getLastName());
                    studentSubject.setSubjectId(subject.getId());
                    studentSubject.setSubjectName(subject.getName());
                    studentSubject.setTeacherFirstName(subject.getTeacherFirstName());
                    studentSubject.setTeacherLastName(subject.getTeacherFirstName());
                    studentSubject.setTeacherId(subject.getTeacherId());
                    studentSubject.setGroupName(finalGroup.getGroupName());
                    studentSubjectDao.save(studentSubject);
                });
            }
        } else {
            throw new EntityNotFoundException("group");
        }
    }

    @Override
    public void createTeacher(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setRoles(List.of("TEACHER"));
        userDao.save(user);
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        return userDao
                .findByRolesContains("TEACHER")
                .stream()
                .map(user -> modelMapper.map(user, TeacherDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUserByRolesContain(String role) {
        return userDao.findByRolesContains(role);
    }

    @Override
    public List<StudentInfo> getStudentInfosByGroupId(String groupId) {
        Group group = groupDao.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group"));
        return group.getStudents();
    }
}
