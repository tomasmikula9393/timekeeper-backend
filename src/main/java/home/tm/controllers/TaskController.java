package home.tm.controllers;

import home.tm.TimeKeeper.api.controllers.TasksApi;
import home.tm.TimeKeeper.api.models.TaskDto;
import home.tm.TimeKeeper.api.models.TaskPaginatedListDto;
import home.tm.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws/rest")
@RequiredArgsConstructor
public class TaskController implements TasksApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskDto> createTask(TaskDto dto) {
        return new ResponseEntity<>(taskService.createTask(dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskDto> getTask(Long id) {
        return new ResponseEntity<>(taskService.getTask(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TaskPaginatedListDto> getTasks(Pageable pageable, String search) {
        return new ResponseEntity<>(taskService.getTasks(pageable, search), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TaskDto> updateTask(Long id, TaskDto dto) {
        return new ResponseEntity<>(taskService.updateTask(id, dto), HttpStatus.OK);
    }
}
