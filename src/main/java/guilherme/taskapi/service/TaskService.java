package guilherme.taskapi.service;

import guilherme.taskapi.model.Task;
import guilherme.taskapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Page<Task> findAllPaginado(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Page<Task> findByCompleted(boolean completed, Pageable pageable) {
        return taskRepository.findByCompleted(completed, pageable);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Task update(Long id, Task taskAtualizada) {
        Task task = findById(id);
        task.setTitle(taskAtualizada.getTitle());
        task.setDescription(taskAtualizada.getDescription());
        task.setCompleted(taskAtualizada.isCompleted());
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }
}