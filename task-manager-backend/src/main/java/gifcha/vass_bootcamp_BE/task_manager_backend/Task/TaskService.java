package gifcha.vass_bootcamp_BE.task_manager_backend.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    /**
     * Saves a task entity.
     * @param task the task to save
     * @return the savedtask 
     */
    Task saveTask(Task task);

    /**
     * Fetches the list of all task entities.
     * @return a list oftask 
     */
    List<Task> fetchTaskList();

    /**
     * Updates an existing task entity.
     * @param task the task with updated information
     * @param taskId the ID of the task to update
     * @return the updatedtask 
     */
    Task updateTask(Task task, UUID taskId);

    /**
      Deletes a task entity by its ID.
      @param taskID the ID of the task to delete
     */
    void deleteTaskById(UUID taskID);
}
