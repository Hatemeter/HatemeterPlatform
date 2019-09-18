package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
 * Servlet implementation class getSnapshotDataMentions
 */
@WebServlet("/getSnapshotDataMentions")
public class getSnapshotDataMentions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
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
		Gson gson = new GsonBuilder().create();
		JsonParser parser = new JsonParser();
		

		try {
			String query = "select * from "+lang+"_mention_stats s,"+lang+"_mention_nets n where n.snapshot = ?  and n.snapshot = s.snapshot";
			PreparedStatement sql = connection.dbconn.prepareStatement(query);
			sql.setString(1, snapshot);
			ResultSet results = sql.executeQuery();

			while (results.next()) {
				chartData = parser.parse(results.getString("connections")).getAsJsonObject();
				netData = parser.parse(results.getString("net")).getAsJsonObject();
			}
			
			//System.out.println(netData.get("nodes").getAsJsonArray());
			
			//result.add("chart", chartData);
			
			//result.add("overall", overallData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		result.add("net", netData);	
		result.add("chartData", chartData);	
		
		try {
			connection.dbconn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		out.write(result.toString());
	}

}
