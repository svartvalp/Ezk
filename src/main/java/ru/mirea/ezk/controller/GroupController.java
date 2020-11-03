package ru.mirea.ezk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ezk.dto.GroupDto;
import ru.mirea.ezk.service.GroupService;

import java.util.List;

@RequestMapping(value = "/group")
@RequiredArgsConstructor
@RestController
public class GroupController {
    private final GroupService groupService;

    @GetMapping(value = "")
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping(value = "/{id}")
    public GroupDto getGroupById(@PathVariable("id") String id)  {
        return groupService.getGroupById(id);
    }

    @PostMapping(value = "")
    public GroupDto createGroup(@RequestParam("groupName") String groupName) {
        return groupService.createGroup(groupName);
    }

}
