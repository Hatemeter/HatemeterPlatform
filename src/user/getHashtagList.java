package user;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.collections.transformation.SortedList;
import lib.ConnectionDB;

/**
 * Servlet implementation class getHashtagList
 */
@WebServlet("/getHashtagList")
public class getHashtagList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getHashtagList() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
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
			String query = "select * from "+lang+"_stats s, "+lang+"_hashtag_nets n where s.snapshot=n.snapshot";
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
