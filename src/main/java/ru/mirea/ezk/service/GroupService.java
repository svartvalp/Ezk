package ru.mirea.ezk.service;

import ru.mirea.ezk.dto.GroupDto;

import java.util.List;

public interface GroupService {
    GroupDto createGroup(String groupName);
    List<GroupDto> getAllGroups();
    GroupDto getGroupById(String groupId);
}
