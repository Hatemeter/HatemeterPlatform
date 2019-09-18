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
 * Servlet implementation class getKeywordMention
 */
@WebServlet("/getKeywordMention")
public class getKeywordMention extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getKeywordMention() {
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
		
		JsonArray keywordsData = new JsonArray();
		JsonParser parser = new JsonParser();
		

		try {
			String query = "select * from "+lang+"_hashkeywords n where n.snapshot = ?  ";
			PreparedStatement sql = connection.dbconn.prepareStatement(query);
			sql.setString(1, snapshot);
			ResultSet results = sql.executeQuery();

			while (results.next()) {
				keywordsData = parser.parse(results.getString("keywords")).getAsJsonArray();
			}
			
			//System.out.println(netData.get("nodes").getAsJsonArray());
			
			//result.add("chart", chartData);
			
			//result.add("overall", overallData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		result.add("keywords", keywordsData);	
	
		try {
			connection.dbconn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		out.write(result.toString());
	}

}
