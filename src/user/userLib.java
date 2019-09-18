package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import lib.ConnectionDB;

public class userLib {

	
	public static JsonObject getUserDetails(String username) {
		
		ConnectionDB connection = new ConnectionDB();
		JsonObject user_details = new JsonObject();

		try {
			String query = "SELECT * FROM users where user_name=?";
			PreparedStatement sql = connection.dbconn.prepareStatement(query);
			sql.setString(1, username);
			ResultSet results = sql.executeQuery();

			while (results.next()) {
				user_details.addProperty("name", results.getString("name"));
				user_details.addProperty("surname", results.getString("surname"));
				user_details.addProperty("type", results.getString("type"));
				user_details.addProperty("lang", results.getString("lang"));
				user_details.addProperty("avatar", results.getString("avatar"));
				user_details.addProperty("privacy", results.getString("privacy"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			connection.dbconn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user_details;
	}
}
