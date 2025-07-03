package gifcha.vass_bootcamp_BE.task_manager_backend.Task;

import java.util.Date;
import java.util.UUID;

import gifcha.vass_bootcamp_BE.task_manager_backend.User.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "tasks")
public class Task {
    @Id
	@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String description;
    private String status;
    private String type;
    private Date createdon = new Date();
	private String assignedto;

    // Constructors
    public Task() {}

    public Task(UUID id, String title, String description, String status, String type, String assignedto) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.type = type;
		this.assignedto = assignedto;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdOn) {
        this.createdon = createdOn;
    }

	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}

	public String getAssignedto() {
		return assignedto;
	}
}
