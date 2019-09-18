package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lib.ConnectionDB;

/**
 * Servlet implementation class getAlertsHashtagList
 */
@WebServlet("/getAlertsHashtagList")
public class getAlertsHashtagList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getAlertsHashtagList() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String username = request.getUserPrincipal().getName();
		

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();

		JsonObject user_details = userLib.getUserDetails(username);
		
		
		
		ConnectionDB connection = new ConnectionDB();
		String lang = user_details.get("lang").getAsString();

		JsonArray hashtaglist = new JsonArray();
		
		
		try {
			String query = "select hashtag from "+lang+"_alerts_trend s";
			PreparedStatement sql = connection.dbconn.prepareStatement(query);
			ResultSet results = sql.executeQuery();

			while (results.next()) {
				

				try {
					hashtaglist.add(results.getString("hashtag"));
				//	snapshotMap.get(stripName).add(results.getString("s.snapshot"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(hashtaglist);

		try {
			connection.dbconn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		out.write(json);
	}

}
