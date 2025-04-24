package gifcha.vass_bootcamp_BE.task_manager_backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	String jdbcUrl;
	Connection connection;

	public Database(String dbName, String username, String password) {
		this.jdbcUrl = "jdbc:postgresql://localhost:5432/"+dbName+"?currentSchema=public&user="+username+"&password=admin";

		// Connect to the database
		try {
			connection = DriverManager.getConnection(jdbcUrl);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.out.println("Failed to connect to db!");
			System.exit(1);
		}
	}

	// returns amount of inserted rows or -1 if failed
	public int addTask(String title, String desc, String type, String status) {
		String query = "INSERT INTO tasks (title, description, status, type) VALUES(?, ?, ?, ?);";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, title);
			statement.setString(2, desc);
			statement.setString(3, status);
			statement.setString(4, type);
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public String[] getTaskList() {
		String query = "SELECT row_to_json(tasks) FROM tasks;"; // each row is converted to json
		try {
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery(query);
			
			ArrayList<String> list = new ArrayList<>();
			while (res.next() == true) {
				String jsonStr = res.getString(1);
				list.add(jsonStr);
			}

			String resArr[] = new String[list.size()];
			list.toArray(resArr);
			return resArr;

		} catch (SQLException e) {
			e.printStackTrace();
			return new String[0];
		}
	}

	// returns amount of changed rows or -1 if failed
	public int removeTaskById(String uuid) {
		String query = "DELETE FROM tasks WHERE tasks.id = ?;";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setObject(1, java.util.UUID.fromString(uuid));
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}

