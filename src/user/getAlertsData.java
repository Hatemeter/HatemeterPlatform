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
 * Servlet implementation class getAlertsData
 */
@WebServlet("/getAlertsData")
public class getAlertsData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getAlertsData() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String username = request.getUserPrincipal().getName();
		

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();

		JsonObject user_details = userLib.getUserDetails(username);
		String hashtag = request.getParameter("hashtag");
		
		
		ConnectionDB connection = new ConnectionDB();
		String lang = user_details.get("lang").getAsString();

		JsonObject hashtagAlertsData = new JsonObject();
		
		JsonParser parser = new JsonParser();
		
		try {
			String query = "select data from "+lang+"_alerts_trend s where s.hashtag=?";
			PreparedStatement sql = connection.dbconn.prepareStatement(query);
			sql.setString(1, hashtag);
			ResultSet results = sql.executeQuery();

			while (results.next()) {
				

				try {
					hashtagAlertsData = parser.parse(results.getString("s.data")).getAsJsonObject();
				//	snapshotMap.get(stripName).add(results.getString("s.snapshot"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(hashtagAlertsData);

		try {
			connection.dbconn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		out.write(json);
	}

}
