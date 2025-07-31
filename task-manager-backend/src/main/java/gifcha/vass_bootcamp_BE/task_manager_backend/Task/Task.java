package gifcha.vass_bootcamp_BE.task_manager_backend.Task;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Column(name = "created_on")
  private Date createdOn = new Date();

  @Column(name = "assigned_to")
  private UUID assignedTo;

  // Constructors
  public Task() {}

  public Task(UUID id, String title, String description, String status, String type, UUID assignedTo) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = status;
    this.type = type;
    this.assignedTo = assignedTo;
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

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public void setAssignedTo(UUID assignedTo) {
    this.assignedTo = assignedTo;
  }

  public UUID getAssignedTo() {
    return assignedTo;
  }
}
