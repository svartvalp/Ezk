package ru.mirea.ezk.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.mirea.ezk.dao.GroupDao;
import ru.mirea.ezk.dto.GroupDto;
import ru.mirea.ezk.entity.Group;
import ru.mirea.ezk.exception.EntityAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupDao groupDao;
    private final ModelMapper modelMapper;

    @Override
    public void createGroup(String groupName) {
        if(groupDao.findByGroupName(groupName) == null) {
            Group group = new Group();
            group.setGroupName(groupName);
            group.setStudents(new ArrayList<>());
            group.setSubjects(new ArrayList<>());
            groupDao.save(group);
        } else {
            throw new EntityAlreadyExistsException("group");
        }
    }

    @Override
    public List<GroupDto> getAllGroups() {
        return groupDao
                .findAll()
                .stream()
                .map(group -> modelMapper.map(group, GroupDto.class))
                .collect(Collectors.toList());
    }
}
