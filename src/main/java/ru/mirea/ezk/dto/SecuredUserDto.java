package ru.mirea.ezk.dto;

import lombok.Data;

import java.util.List;

@Data
public class SecuredUserDto {
    private String id;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
