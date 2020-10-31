package ru.mirea.ezk.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("studentSubject")
public class StudentSubject {
    @Id
    private String id;
    private String groupId;
    private String groupName;
    private String studentId;
    private String studentFirstName;
    private String studentLastName;
    private String subjectId;
    private String subjectName;
    private String teacherId;
    private String teacherFirstName;
    private String teacherLastName;
    private String mark;
}
