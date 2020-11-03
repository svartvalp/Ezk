package ru.mirea.ezk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ezk.entity.StudentSubject;
import ru.mirea.ezk.service.StudentSubjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/studentSubject")
public class StudentSubjectController {
    private final StudentSubjectService studentSubjectService;

    @GetMapping(value = "")
    public List<StudentSubject> getStudentSubjectByGroupId(@RequestParam("groupId") String groupId) {
        return studentSubjectService.getStudentSubjectsByGroupId(groupId);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<StudentSubject> getStudentSubjectByTeacherId(@PathVariable("teacherId") String teacherId) {
        return studentSubjectService.getSubjectsByTeacherId(teacherId);
    }

    @GetMapping("/student/{studentId}")
    public List<StudentSubject> getStudentSubjectsByStudentId(@PathVariable("studentId") String studentId) {
        return studentSubjectService.getSubjectsByStudentId(studentId);
    }

    @GetMapping("/group/{groupId}/subject/{subjectId}")
    public List<StudentSubject> getStudentSubjectByGroupIdAndSubjectId(@PathVariable("groupId") String groupId, @PathVariable("subjectId") String subjectId) {
        return studentSubjectService.getSubjectsByGroupIdAndSubjectId(groupId, subjectId);
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{id}/mark")
    public void setMark(@PathVariable("id") String subjectId, @RequestParam("value") String value) {
        studentSubjectService.setMark(subjectId, value);
    }
}
