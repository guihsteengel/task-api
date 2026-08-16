package guilherme.taskapi.controller;

import guilherme.taskapi.dto.TaskDTO;
import guilherme.taskapi.mapper.TaskMapper;
import guilherme.taskapi.model.Task;
import guilherme.taskapi.model.User;
import guilherme.taskapi.repository.UserRepository;
import guilherme.taskapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserRepository userRepository;

    private User getUsuarioLogado(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @GetMapping
    public Page<TaskDTO> findAll(
            @RequestParam(required = false) Boolean completed,
            Pageable pageable,
            Authentication authentication) {

        User user = getUsuarioLogado(authentication);

        Page<Task> tasks;
        if (completed != null) {
            tasks = taskService.findByCompleted(user, completed, pageable);
        } else {
            tasks = taskService.findAllPaginado(user, pageable);
        }

        return tasks.map(taskMapper::toDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findById(@PathVariable Long id, Authentication authentication) {
        User user = getUsuarioLogado(authentication);
        Task task = taskService.findById(id, user);
        return ResponseEntity.ok(taskMapper.toDTO(task));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskDTO dto, Authentication authentication) {
        User user = getUsuarioLogado(authentication);
        Task task = taskMapper.toEntity(dto);
        Task criada = taskService.create(task, user);
        return ResponseEntity.ok(taskMapper.toDTO(criada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @Valid @RequestBody TaskDTO dto, Authentication authentication) {
        User user = getUsuarioLogado(authentication);
        Task task = taskMapper.toEntity(dto);
        Task atualizada = taskService.update(id, task, user);
        return ResponseEntity.ok(taskMapper.toDTO(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        User user = getUsuarioLogado(authentication);
        taskService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}