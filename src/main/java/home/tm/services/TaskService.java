package home.tm.services;

import home.tm.TimeKeeper.api.models.TaskDto;
import home.tm.TimeKeeper.api.models.TaskPaginatedListDto;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    TaskDto createTask(TaskDto dto);

    void deleteTask(Long id);

    TaskDto getTask(Long id);

    TaskPaginatedListDto getTasks(Pageable pageable, String search);

    TaskDto updateTask(Long id, TaskDto dto);

    TaskDto taskDone(Long id);
}
