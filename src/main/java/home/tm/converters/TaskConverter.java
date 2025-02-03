package home.tm.converters;

import home.tm.TimeKeeper.api.models.TaskDto;
import home.tm.model.Task;
import home.tm.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskConverter {

    private final SecurityService securityService;

    public TaskDto toDto(Task entity) {
        return toDto(entity, new TaskDto());
    }

    private TaskDto toDto(Task entity, TaskDto dto) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        dto.setDeadline(entity.getDeadline());
        return dto;
    }

    public Task toEntity(Task entity, TaskDto dto) {
        entity.setId(dto.getId() == null ? null : dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setDeadline(dto.getDeadline());
        return entity;
    }

    public Task toEntity(TaskDto dto) {
        Task task = toEntity(new Task(), dto);
        task.setUser(securityService.getCurrentUser());
        return task;
    }

    public List<TaskDto> toListDto(List<Task> data) {
        return data.stream().map(this::toDto).collect(Collectors.toList());
    }
}
