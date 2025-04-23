package gifcha.vass_bootcamp_BE.task_manager_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TaskManagerBackendApplication {

	static String dbName = "taskdb";
	static String username = "postgres";
	static String password = "admin";
	static Database db;

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerBackendApplication.class, args);
		db = new Database(dbName, username, password);
	}

	@GetMapping("/add-task")
	public int addTask(
		@RequestParam(value = "title") String title,
		@RequestParam(value = "desc") String desc,
		@RequestParam(value = "status") String status,
		@RequestParam(value = "type") String type )
		{
			return db.addTask(title, desc, status, type);
		}

	@GetMapping("/get-task-list")
	public String[] getTaskList() {
			return db.getTaskList();
		}


	@GetMapping("/delete-task")
	public int editText(@RequestParam(value = "uuid") String uuid) {
		return db.removeTaskById(uuid);
	}
}

