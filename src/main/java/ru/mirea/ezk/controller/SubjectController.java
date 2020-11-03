package ru.mirea.ezk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ezk.dto.SubjectDto;
import ru.mirea.ezk.entity.Subject;
import ru.mirea.ezk.service.SubjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "")
    public Subject createSubject(@RequestBody SubjectDto subjectDto) {
        return subjectService.createSubject(subjectDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable("id") String id) {
        subjectService.deleteSubject(id);
    }
    @GetMapping("/group/{id}")
    public List<Subject> getSubjectsByGroupId(@PathVariable("id") String id) {
        return subjectService.getAllSubjectsByGroupId(id);
    }
}
