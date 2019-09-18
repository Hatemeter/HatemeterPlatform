package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import user.userLib;

/**
 * Servlet implementation class counterNarrativeBot
 */
@WebServlet("/counterNarrativeBot")
public class counterNarrativeBot extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String message = request.getParameter("message");

		
		String username = request.getUserPrincipal().getName();
		
		
		JsonObject user_details = userLib.getUserDetails(username);
		String lang = user_details.get("lang").getAsString();
		
		switch (lang) {
		 case "en":
			 lang = "english";
			 break;
		 case "it":
			 lang = "italian";
			 break;
		 case "fr":
			 lang = "french";
			 break;
		}
		
		//System.out.println(message);
		
		ProcessBuilder builder = new ProcessBuilder();
		//server
		builder.command("/usr/bin/python3", "/data/giovanni/hatemeter/counter_narrative_generator/cn_generator.py", "--hate_speech", "\""+ message+ "\"", "--response_num", "4", "--language",   lang);
		
		
		
		//local
		//builder.command("/usr/local/bin/python3", "/data/giovanni/hatemeter/counter_narrative_generator/cn_generator.py", "--hate_speech", "\""+ message+ "\"", "--response_num", "4", "--language",   lang);
		//builder.command("/usr/local/Cellar/python/3.7.3/Frameworks/Python.framework/Versions/3.7/bin/python3", "/data/giovanni/hatemeter/counter_narrative_generator/cn_generator.py", "--hate_speech", "\""+ message+ "\"", "--response_num", "4", "--language",   lang);
		
		

		System.err.println(builder.command());
		Process process = builder.start();
		OutputStream os = process.getOutputStream();
		PrintWriter pWriter = new PrintWriter(os);

		pWriter.flush();
		pWriter.close();
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);

		BufferedReader br = new BufferedReader(isr);
		String line;

		StringBuffer sb = new StringBuffer();

		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append(System.getProperty("line.separator"));

		}
		//System.out.println(sb.toString());
		//System.out.println("end python");
		JsonParser parser = new JsonParser();
		JsonElement arrayElement = parser.parse(sb.toString());
		JsonArray responses = arrayElement.getAsJsonArray();

		out.write(responses.toString());

	}

}
