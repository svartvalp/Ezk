package ru.mirea.ezk.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("subject")
public class Subject {
    @Id
    private String id;
    private String groupId;
    private String groupName;
    private String name;
    private String teacherId;
    private String teacherFirstName;
    private String teacherLastName;
}
