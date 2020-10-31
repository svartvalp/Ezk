package ru.mirea.ezk.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("user")
public class User {
    @Id
    private String id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
