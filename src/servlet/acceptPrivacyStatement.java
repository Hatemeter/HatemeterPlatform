package servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.ConnectionDB;

/**
 * Servlet implementation class acceptPrivacyStatement
 */
@WebServlet("/acceptPrivacyStatement")
public class acceptPrivacyStatement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public acceptPrivacyStatement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		 String username = request.getUserPrincipal().getName();

		ConnectionDB connection = new ConnectionDB();

		 SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy",Locale.ENGLISH);
		Calendar cal = Calendar.getInstance();
		String acceptanceDate = sdf.format(cal.getTime());
		
		
		
		String query = "update users set privacy=? where user_name = ?";
		try {
			PreparedStatement sql = connection.dbconn.prepareStatement(query);
			sql.setString(1, acceptanceDate);
			sql.setString(2, username);
			sql.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection.dbconn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.html");
		dispatcher.forward(request, response);

	}

}
