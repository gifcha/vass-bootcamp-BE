package gifcha.vass_bootcamp_BE.task_manager_backend.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository TaskRepository;

    @GetMapping
    public List<Task> getAllTasks() {
        return TaskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable UUID id) {
        return TaskRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return TaskRepository.save(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable UUID id, @RequestBody Task updatedTask) {
        Task task = TaskRepository.findById(id).orElseThrow();
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setType(updatedTask.getType());
        task.setCreatedon(updatedTask.getCreatedon());
        return TaskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id) {
        TaskRepository.deleteById(id);
    }
}
