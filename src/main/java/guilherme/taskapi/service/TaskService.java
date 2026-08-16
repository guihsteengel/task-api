package guilherme.taskapi.service;

import guilherme.taskapi.model.Task;
import guilherme.taskapi.model.User;
import guilherme.taskapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> findAllPaginado(User user, Pageable pageable) {
        return taskRepository.findByUser(user, pageable);
    }

    public Page<Task> findByCompleted(User user, boolean completed, Pageable pageable) {
        return taskRepository.findByUserAndCompleted(user, completed, pageable);
    }

    public Task findById(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new TaskNotFoundException(id);
        }

        return task;
    }

    public Task create(Task task, User user) {
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task update(Long id, Task taskAtualizada, User user) {
        Task task = findById(id, user);
        task.setTitle(taskAtualizada.getTitle());
        task.setDescription(taskAtualizada.getDescription());
        task.setCompleted(taskAtualizada.isCompleted());
        return taskRepository.save(task);
    }

    public void delete(Long id, User user) {
        Task task = findById(id, user);
        taskRepository.delete(task);
    }
}