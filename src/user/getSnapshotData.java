package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lib.ConnectionDB;

/**
 * Servlet implementation class getSnapshotData
 */
@WebServlet("/getSnapshotData")
public class getSnapshotData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getSnapshotData() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		 String username = request.getUserPrincipal().getName();
		response.setCharacterEncoding("UTF-8");
		System.setProperty("file.encoding", "UTF8");
		PrintWriter out = response.getWriter();

		JsonObject user_details = userLib.getUserDetails(username);
		
		String lang = user_details.get("lang").getAsString();
		ConnectionDB connection = new ConnectionDB();
		
		String snapshot = request.getParameter("snapshot");
		
		JsonObject result = new JsonObject();
		
		JsonObject chartData = new JsonObject();
		JsonArray overallData = new JsonArray();
		JsonObject netData = new JsonObject();
		JsonArray topRetweet = new JsonArray();
		Gson gson = new GsonBuilder().create();
		JsonParser parser = new JsonParser();
		

		try {
			String query = "select * from "+lang+"_stats s, "+lang+"_hashtag_nets n where s.snapshot = ? and s.snapshot=n.snapshot";
			PreparedStatement sql = connection.dbconn.prepareStatement(query);
			sql.setString(1, snapshot);
			ResultSet results = sql.executeQuery();

			while (results.next()) {
				chartData = parser.parse(results.getString("daily")).getAsJsonObject();
				netData = parser.parse(results.getString("net")).getAsJsonObject();
				overallData = parser.parse(results.getString("overall")).getAsJsonArray();
			}
			
			query = "select * from "+lang+"_top_retweet s where s.snapshot = ?";
			sql = connection.dbconn.prepareStatement(query);
			sql.setString(1, snapshot);
			results = sql.executeQuery();


			while (results.next()) {
				topRetweet = parser.parse(results.getString("results")).getAsJsonArray();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.add("chart", chartData);
		result.add("net", netData);	
		result.add("overall", overallData);
		result.add("topretweet", topRetweet);
		
		
		try {
			connection.dbconn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		out.write(result.toString());
	}

}
