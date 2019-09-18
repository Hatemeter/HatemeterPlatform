package twitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lib.ConnectionDB;
import lib.Utilities;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;
import user.userLib;

/**
 * Servlet implementation class generalTrendLastUsage
 */
@WebServlet("/generalTrendLastUsage")
public class generalTrendLastUsage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public generalTrendLastUsage() {
		super();
		// TODO Auto-generated constructor stub
	}

	private TreeMap<String, TreeSet<String>> getSnapshotMap(String username) {
		
		JsonObject user_details = userLib.getUserDetails(username);
		ConnectionDB connection = new ConnectionDB();
		String lang = user_details.get("lang").getAsString();

		JsonObject hashtaglist = new JsonObject();
		TreeMap<String, TreeSet<String>> snapshotMap = new TreeMap<>();

		try {
			String query = "select * from " + lang + "_stats s, " + lang
					+ "_hashtag_nets n where s.snapshot=n.snapshot";
			PreparedStatement sql = connection.dbconn.prepareStatement(query);
			ResultSet results = sql.executeQuery();

			while (results.next()) {
				String stripName = results.getString("s.snapshot").split("-", 3)[2].replace(".json", "") ;
				if (!snapshotMap.containsKey(stripName)) {
					snapshotMap.put(stripName, new TreeSet<>(Collections.reverseOrder()));
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

		try {
			connection.dbconn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return snapshotMap;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		ServletContext context = request.getServletContext();

		PrintWriter out = response.getWriter();
		

		
		 String username = request.getUserPrincipal().getName();

		JsonObject user_details = userLib.getUserDetails(username);
		String lang = user_details.get("lang").getAsString();

		Map<String, Calendar> latestUsed = new HashMap<>();

		
		Twitter twitter = Utilities.getTwitterObject(context);
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		
		 JsonObject latestUsedArray = new JsonObject();
		
		try {

			for (String needle : getSnapshotMap(username).keySet()) {

				Query query = new Query("\"" + needle + "\""); // + " -filter:retweets"
				query.lang(lang);
				query.resultType(Query.RECENT);
				query.count(1);
				QueryResult result = twitter.search(query);

				JsonArray results = new JsonArray();

				for (Status status : result.getTweets()) {
					JsonObject jo = parser.parse(TwitterObjectFactory.getRawJSON(status)).getAsJsonObject();
					results.add(jo);
					
					
					Date creation_date = sf.parse(jo.get("created_at").getAsString());
                    Calendar cal = Calendar.getInstance();
                    
                    cal.setTime(creation_date);
					latestUsed.put(needle,cal);
					
				}
			
			}
			
			 Map<String, Calendar> result = latestUsed.entrySet().stream()
		                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		                .limit(5)
		                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
		                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			 SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss",Locale.ENGLISH);
			 
			 
		
			 
			 result.forEach((k,v) ->{
				
				 latestUsedArray.addProperty(k,sdf.format(v.getTime()));
			 });
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(latestUsedArray);

	}

}
