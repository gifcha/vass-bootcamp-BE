package gifcha.vass_bootcamp_BE.task_manager_backend.Task;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

    @GetMapping
    public List<Task> getAllTasks() {
		return taskService.getTaskList();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id);
	}

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable UUID id, @RequestBody Task updatedTask) {
		return this.taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public List<Task> deleteTask(@PathVariable UUID id) {
		// returns updated task list after deletion
		return taskService.deleteTaskById(id);
    }
}
