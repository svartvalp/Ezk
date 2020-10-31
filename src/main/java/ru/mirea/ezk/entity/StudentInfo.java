package ru.mirea.ezk.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("studentInfo")
public class StudentInfo {
    @Id
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String groupId;
    private String groupName;
}
