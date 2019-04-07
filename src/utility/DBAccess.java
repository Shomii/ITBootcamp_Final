package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.api.jdbc.Statement;

public class DBAccess {

	// get data from database and displays them in eclipse console
	public static void getDataFromDataBase() throws SQLException {

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/id9014543_izlet", "root", "");
				Statement statement = (Statement) connection.createStatement()) {

			// get all users
			final ResultSet users = statement.executeQuery("SELECT * FROM users");

			String leftAlignFormat = "| %-2s | %-15s | %-17s | %-16s | %-30s |%n";
			System.out.println("\n\n ------ USERS ----------");
			System.out.format(
					"+----+-----------------+-------------------+------------------+--------------------------------+%n");
			System.out.format(
					"| ID |    First name   |      Last name    |     user name    |              email             |%n");
			System.out.format(
					"+----+-----------------+-------------------+------------------+--------------------------------+%n");

			while (users.next()) {

				int id = users.getInt(1);
				String fname = users.getString(2);
				String lname = users.getString(3);
				String username = users.getString(4);
				String email = users.getString(6);

				System.out.format(leftAlignFormat, id, fname, lname, username, email);

				System.out.format(
						"+----+-----------------+-------------------+------------------+--------------------------------+%n");
			}

			// get all posts
			final ResultSet posts = statement.executeQuery("SELECT * FROM posts");

			String leftAlignFormat2 = "| %-6s | %-15s | %-17s | %-7s | %-28s | %-13s |%n";
			System.out.println("\n\n ------ POSTS ----------");
			System.out.format(
					"+--------+-----------------+-------------------+---------+------------------------------+---------------+%n");
			System.out.format(
					"| PostID |    PostHeader   |      Post date    | user ID |         Post location        |   Transport   |%n");
			System.out.format(
					"+--------+-----------------+-------------------+---------+------------------------------+---------------+%n");

			while (posts.next()) {

				int postID = posts.getInt(1);
				String postHeader = posts.getString(3);
				String postDate = posts.getString(4);
				int userID = posts.getInt(6);
				String postLocation = posts.getString(7);
				String transport = posts.getString(8);

				System.out.format(leftAlignFormat2, postID, postHeader, postDate, userID, postLocation, transport);

				System.out.format(
						"+--------+-----------------+-------------------+---------+------------------------------+---------------+%n");
			}
		}
	}
}
