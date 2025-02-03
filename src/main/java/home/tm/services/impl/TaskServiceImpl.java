package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.TaskDto;
import home.tm.TimeKeeper.api.models.TaskPaginatedListDto;
import home.tm.converters.TaskConverter;
import home.tm.exceptions.NotFoundException;
import home.tm.model.Task;
import home.tm.repositories.TaskRepository;
import home.tm.security.service.SecurityService;
import home.tm.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static home.tm.exceptions.ExceptionMessage.TASK_WAS_NOT_FOUND;
import static home.tm.exceptions.ExceptionMessage.USER_IS_NOT_AUTHORIZED;
import static home.tm.exceptions.ExceptionType.NEBYLO_NALEZENO;
import static home.tm.exceptions.SeverityEnum.ERROR;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final SecurityService securityService;
    private final TaskConverter taskConverter;

    @Override
    public TaskDto createTask(TaskDto dto) {
        Task task = taskRepository.save(taskConverter.toEntity(dto));
        return taskConverter.toDto(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.delete(getTaskById(id));
    }

    @Override
    public TaskDto getTask(Long id) {
        return taskConverter.toDto(getTaskById(id));
    }

    @Override
    public TaskPaginatedListDto getTasks(Pageable pageable, String search) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        TaskPaginatedListDto dto = new TaskPaginatedListDto();
        dto.setList(taskConverter.toListDto(tasks.getContent()));
        dto.setCount(tasks.getPageable().getPageSize());
        dto.setPage(tasks.getPageable().getPageNumber());
        return dto;
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto dto) {
        Task task = taskRepository.save(taskConverter.toEntity(getTaskById(id), dto));
        return taskConverter.toDto(task);
    }

    private Task getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new NotFoundException(NEBYLO_NALEZENO, String.format(TASK_WAS_NOT_FOUND.getMessage(), id), ERROR)
        );
        Long userId = securityService.getCurrentUser().getId();
        if (!task.getUser().getId().equals(userId)) {
            throw new NotFoundException(NEBYLO_NALEZENO, String.format(USER_IS_NOT_AUTHORIZED.getMessage(), userId), ERROR);
        }
        return task;
    }
}
