package ru.mirea.ezk.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("group")
public class Group {
    @Id
    private String id;
    private String groupName;
    private List<StudentInfo> students;
    private List<Subject> subjects;
}
