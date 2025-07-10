package gifcha.vass_bootcamp_BE.task_manager_backend.Security;

public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
		this.token = token;
	}

    public String getToken() {
		return token;
	}
}

