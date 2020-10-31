package ru.mirea.ezk.service;

import ru.mirea.ezk.dto.GroupDto;

import java.util.List;

public interface GroupService {
    void createGroup(String groupName);
    List<GroupDto> getAllGroups();
}
