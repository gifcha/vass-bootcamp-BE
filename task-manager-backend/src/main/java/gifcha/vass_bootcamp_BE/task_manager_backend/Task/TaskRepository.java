package gifcha.vass_bootcamp_BE.task_manager_backend.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

}
