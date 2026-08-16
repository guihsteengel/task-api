package guilherme.taskapi.repository;

import guilherme.taskapi.model.Task;
import guilherme.taskapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByUser(User user, Pageable pageable);

    Page<Task> findByUserAndCompleted(User user, boolean completed, Pageable pageable);
}