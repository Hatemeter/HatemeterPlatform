package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lib.ConnectionDB;
import user.userLib;

/**
 * Servlet implementation class saveCounterNarrativeSession
 */
@WebServlet("/saveCounterNarrativeSession")
public class saveCounterNarrativeSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public saveCounterNarrativeSession() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		System.out.println(request);
		String sessionString = request.getParameter("data");

		
		String username = request.getUserPrincipal().getName();
		
		JsonParser parser = new JsonParser();
		JsonObject session = parser.parse(sessionString).getAsJsonObject();
		ConnectionDB connection = new ConnectionDB();

		
		
		JsonObject user_details = userLib.getUserDetails(username);
		String lang = user_details.get("lang").getAsString();
		try {
		  PreparedStatement pstmt = connection.dbconn.prepareStatement("REPLACE INTO session_storage VALUES (?,?,?,?)");
          pstmt.setString(1, session.get("id").getAsString());
          pstmt.setString(2, username);
          pstmt.setString(3, lang);
          pstmt.setString(4, sessionString);
          pstmt.execute();
          connection.dbconn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
