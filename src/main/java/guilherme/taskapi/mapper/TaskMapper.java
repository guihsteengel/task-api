package guilherme.taskapi.mapper;

import guilherme.taskapi.dto.TaskDTO;
import guilherme.taskapi.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt()
        );
    }

    public Task toEntity(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        return task;
    }
}