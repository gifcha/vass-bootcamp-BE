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

	String ResultSetToJsonString(ResultSet set) {
		try {
			String result = "{\"id\": \""+set.getString(1) + "\"," +
			"\"title\": \""+set.getString(2) + "\"," +
			"\"desc\": \""+set.getString(3) + "\"," +
			"\"status\": \""+set.getString(4) + "\"," +
			"\"type\": \""+set.getString(5) + "\"," +
			"\"createdOn\": \""+set.getString(6) + "\"}";

			return result;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return "";
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
		String query = "SELECT * FROM tasks;";
		try {
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery(query);
			
			ArrayList<String> list = new ArrayList<>();
			while (res.next() == true) {
				String jsonStr = ResultSetToJsonString(res);
				list.add(jsonStr);
			}

			String[] result = new String[list.size()];
			list.toArray(result);

			return result;
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

