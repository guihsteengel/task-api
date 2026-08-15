package guilherme.taskapi.controller;

import guilherme.taskapi.dto.TaskDTO;
import guilherme.taskapi.mapper.TaskMapper;
import guilherme.taskapi.model.Task;
import guilherme.taskapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    public Page<TaskDTO> findAll(
            @RequestParam(required = false) Boolean completed,
            Pageable pageable) {

        Page<Task> tasks;
        if (completed != null) {
            tasks = taskService.findByCompleted(completed, pageable);
        } else {
            tasks = taskService.findAllPaginado(pageable);
        }

        return tasks.map(taskMapper::toDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return ResponseEntity.ok(taskMapper.toDTO(task));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskDTO dto) {
        Task task = taskMapper.toEntity(dto);
        Task criada = taskService.create(task);
        return ResponseEntity.ok(taskMapper.toDTO(criada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @Valid @RequestBody TaskDTO dto) {
        Task task = taskMapper.toEntity(dto);
        Task atualizada = taskService.update(id, task);
        return ResponseEntity.ok(taskMapper.toDTO(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}