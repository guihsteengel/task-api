package guilherme.taskapi.service;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task não encontrada com id: " + id);
    }
}