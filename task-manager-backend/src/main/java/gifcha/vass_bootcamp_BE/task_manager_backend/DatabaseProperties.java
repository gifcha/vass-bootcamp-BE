package gifcha.vass_bootcamp_BE.task_manager_backend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "db")
public class DatabaseProperties {
    private String username;
    private String password;
    private String name;

    // Getters and setters
    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getName() { return name; }
}
