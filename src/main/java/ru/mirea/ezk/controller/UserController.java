package ru.mirea.ezk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ezk.dto.SecuredUserDto;
import ru.mirea.ezk.dto.StudentDto;
import ru.mirea.ezk.dto.TeacherDto;
import ru.mirea.ezk.dto.UserDto;
import ru.mirea.ezk.entity.StudentInfo;
import ru.mirea.ezk.entity.User;
import ru.mirea.ezk.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/student")
    public void createStudent(@RequestBody StudentDto studentDto, @RequestParam("groupId") String groupId) {
        userService.createStudent(groupId, studentDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/teacher")
    public void createTeacher(@RequestBody UserDto userDto) {
        userService.createTeacher(userDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/info")
    public SecuredUserDto getUserInfo(Principal principal) {
        return userService.getUserInfo(principal);
    }

    @GetMapping("/student/info/{userId}")
    public StudentInfo getStudentInfo(@PathVariable("userId") String userId) {
        return userService.getStudentInfoByUserId(userId);
    }

    @GetMapping(value = "/role/{role}")
    public List<User> getUserRoleContain(@PathVariable("role") String role) {
        return userService.getUserByRolesContain(role);
    }

    @GetMapping(value = "/teacher")
    public List<TeacherDto> getTeachers() {
        return userService.getAllTeachers();
    }

    @GetMapping("/student/group/{groupId}")
    public List<StudentInfo> getStudentsByGroupId(@PathVariable("groupId") String groupId) {
        return userService.getStudentInfosByGroupId(groupId);
    }


}
