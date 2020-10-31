package ru.mirea.ezk.service;

import ru.mirea.ezk.dto.SecuredUserDto;
import ru.mirea.ezk.dto.StudentDto;
import ru.mirea.ezk.dto.TeacherDto;
import ru.mirea.ezk.dto.UserDto;
import ru.mirea.ezk.entity.StudentInfo;
import ru.mirea.ezk.entity.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    void init();
    void deleteUser(String userId);
    SecuredUserDto getUserInfo(Principal principal);
    StudentInfo getStudentInfoByUserId(String userId);
    void createStudent(String groupId, StudentDto studentDto);
    void createTeacher(UserDto userDto);
    List<TeacherDto> getAllTeachers();
    List<User> getUserByRolesContain(String role);
    List<StudentInfo> getStudentInfosByGroupId(String groupId);
}
