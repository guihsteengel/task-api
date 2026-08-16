package guilherme.taskapi.service;

import guilherme.taskapi.model.Task;
import guilherme.taskapi.model.User;
import guilherme.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Usuario Teste");
        user.setEmail("teste@teste.com");

        task = new Task();
        task.setId(1L);
        task.setTitle("Estudar Spring");
        task.setDescription("Revisar conceitos");
        task.setCompleted(false);
        task.setUser(user);
    }

    @Test
    void deveRetornarTaskPorId() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task resultado = taskService.findById(1L, user);

        assertEquals("Estudar Spring", resultado.getTitle());
    }

    @Test
    void deveLancarExcecaoQuandoTaskNaoExiste() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.findById(99L, user));
    }

    @Test
    void deveLancarExcecaoQuandoTaskNaoPertenceAoUsuario() {
        User outroUsuario = new User();
        outroUsuario.setId(2L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(TaskNotFoundException.class, () -> taskService.findById(1L, outroUsuario));
    }

    @Test
    void deveCriarTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task resultado = taskService.create(task, user);

        assertNotNull(resultado);
        assertEquals("Estudar Spring", resultado.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void deveDeletarTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.delete(1L, user);

        verify(taskRepository, times(1)).delete(task);
    }
}