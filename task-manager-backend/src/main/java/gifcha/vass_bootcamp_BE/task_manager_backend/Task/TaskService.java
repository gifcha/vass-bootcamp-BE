package gifcha.vass_bootcamp_BE.task_manager_backend.Task;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

    Task addTask(Task task) {
        return taskRepository.save(task);
	}

    List<Task> getTaskList() {
        return taskRepository.findAll();
	}

	Task getTaskById(UUID id) {
        return taskRepository.findById(id).orElseThrow();
	}

    Task updateTask(UUID id, Task updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setType(updatedTask.getType());
        task.setCreatedon(updatedTask.getCreatedon());
        return taskRepository.save(task);
	}

    void deleteTaskById(UUID id) {
        taskRepository.deleteById(id);
	}
}
