package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import lib.ConnectionDB;

/**
 * Servlet implementation class getMentionsList
 */
@WebServlet("/getMentionsList")
public class getMentionsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getMentionsList() {
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

		JsonObject hashtaglist = new JsonObject();
		TreeMap<String, TreeSet<String>> snapshotMap = new TreeMap<>();

		
		try {
			String query = "select * from "+lang+"_mention_stats s, "+lang+"_mention_nets n where s.snapshot=n.snapshot";
			PreparedStatement sql = connection.dbconn.prepareStatement(query);
			ResultSet results = sql.executeQuery();

			while (results.next()) {
				String stripName = results.getString("s.snapshot").split("-",3)[2].replace(".json", "");
				if (!snapshotMap.containsKey(stripName)) {
					snapshotMap.put(stripName, new TreeSet<>( Collections.reverseOrder() ));
				}

				try {
					snapshotMap.get(stripName).add(results.getString("s.snapshot"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(snapshotMap);

		try {
			connection.dbconn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		out.write(json);
	}

}
