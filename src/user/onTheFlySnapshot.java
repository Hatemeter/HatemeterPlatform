package user;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import eu.fbk.dh.kd.annotator.DigiKDAnnotations;
import eu.fbk.dh.kd.annotator.DigiKDResult;
import eu.fbk.dh.tint.runner.TintPipeline;
import lib.GraphForTags;
import lib.Utilities;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Servlet implementation class onTheFlySnapshot
 */
@WebServlet("/onTheFlySnapshot")
public class onTheFlySnapshot extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public onTheFlySnapshot() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		ServletContext context = request.getServletContext();
		String languagePackPath = request.getServletContext().getRealPath("/WEB-INF/lib/languages");
		
		String username = request.getUserPrincipal().getName();

		JsonObject user_details = userLib.getUserDetails(username);
		String lang = user_details.get("lang").getAsString();

		String querySnapshot = request.getParameter("snapshot");
		// System.out.println(querySnapshot);
		// twitter4j auth
	
		Twitter twitter = Utilities.getTwitterObject(context);

		JsonParser parser = new JsonParser();

		GraphForTags globalStatsForHashTag = new GraphForTags();

		JsonObject snapshotnet = new JsonObject();
		Object pipeline = null;
		if (lang.equals("it")) {
			pipeline = (TintPipeline) context.getAttribute("it_pipeline");
		} else if (lang.equals("en")) {
			pipeline = (StanfordCoreNLP) context.getAttribute("en_pipeline");

		} else if (lang.equals("fr")) {
			pipeline = (StanfordCoreNLP) context.getAttribute("fr_pipeline");
		}
		StringBuffer alltext = new StringBuffer();

		JsonArray messages = new JsonArray();

		try {

			Query query = new Query("\"" + querySnapshot + "\""); // + " -filter:retweets"
			query.lang(lang);
			query.resultType(Query.RECENT);
			query.count(100);
			QueryResult result = twitter.search(query);
			//System.out.println("remains "+result.getRateLimitStatus().getRemaining());
			String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
			SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
			sf.setLenient(true);

			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm:ss", Locale.ENGLISH);

			ArrayList<JsonObject> results = new ArrayList<>();
			for (Status status : result.getTweets()) {
				JsonObject jo = parser.parse(TwitterObjectFactory.getRawJSON(status)).getAsJsonObject();

				Date creation_date = sf.parse(jo.get("created_at").getAsString());
				Calendar cal = Calendar.getInstance();
				cal.setTime(creation_date);
				if (jo.get("retweeted_status") != null) {
					String text = jo.getAsJsonObject("retweeted_status").get("text").getAsString();

					String by = jo.get("user").getAsJsonObject().get("screen_name").getAsString();
					String profileImgUrl = jo.get("user").getAsJsonObject().get("profile_image_url").getAsString();
					String fromUser = jo.getAsJsonObject("retweeted_status").get("user").getAsJsonObject()
							.get("screen_name").getAsString();

					alltext.append(text + "\n");

					JsonObject retweet = new JsonObject();
					
					retweet.addProperty("id", jo.get("id_str").getAsString());

					retweet.addProperty("type", "rt");
					retweet.addProperty("text", text);
					retweet.addProperty("by", by);
					retweet.addProperty("fromUser", fromUser);
					retweet.addProperty("date", sdf.format(cal.getTime()));
					retweet.addProperty("profileImgUrl", profileImgUrl);
					
					// System.out.println(retweet.toString());

					messages.add(retweet);
					JsonArray hashtags = jo.getAsJsonObject("retweeted_status").getAsJsonObject("entities").getAsJsonArray("hashtags");
					if (hashtags.size() > 1) {
						//System.out.println(jo.toString());
						//System.out.println(hashtags.toString());
						for (int x = 0; x < hashtags.size() - 1; x++) {
							for (int j = x + 1; j < hashtags.size(); j++) {
								String tag1 = "#"
										+ hashtags.get(x).getAsJsonObject().get("text").getAsString().toLowerCase();
								String tag2 = "#"
										+ hashtags.get(j).getAsJsonObject().get("text").getAsString().toLowerCase();
								if (!tag1.equals(tag2)) {
									globalStatsForHashTag.addEdge(tag1, tag2);
								}
							}
						}

					}
				} else {
					// System.out.println(tweet.toString());
					// text = tweet.getAsJsonObject("retweeted_status").get("text").getAsString();
					String text = jo.get("text").getAsString();
					String by = jo.get("user").getAsJsonObject().get("screen_name").getAsString();
					String profileImgUrl = jo.get("user").getAsJsonObject().get("profile_image_url").getAsString();
					
					
					JsonObject tweet = new JsonObject();
					tweet.addProperty("id", jo.get("id_str").getAsString());
					tweet.addProperty("type", "t");
					tweet.addProperty("text", text);
					tweet.addProperty("by", by);
					tweet.addProperty("date", sdf.format(cal.getTime()));
					tweet.addProperty("profileImgUrl", profileImgUrl);
					// System.out.println(retweet.toString());

					messages.add(tweet);

					alltext.append(text + "\n");
					JsonArray hashtags = jo.getAsJsonObject("entities").getAsJsonArray("hashtags");
					if (hashtags.size() > 1) {
						//System.out.println(jo.toString());
						//System.out.println(hashtags.toString());
						for (int x = 0; x < hashtags.size() - 1; x++) {
							for (int j = x + 1; j < hashtags.size(); j++) {
								String tag1 = "#"
										+ hashtags.get(x).getAsJsonObject().get("text").getAsString().toLowerCase();
								String tag2 = "#"
										+ hashtags.get(j).getAsJsonObject().get("text").getAsString().toLowerCase();
								if (!tag1.equals(tag2)) {
									globalStatsForHashTag.addEdge(tag1, tag2);
								}
							}
						}

					}
				}

				

				

			}
			
		
			JsonArray keywords = new JsonArray();

			if (lang.equals("en")) {
				Annotation annotation = new Annotation(
						alltext.toString().replaceAll("#[A-Za-z]+", "").replaceAll("@[A-Za-z]+", ""));
				((StanfordCoreNLP) pipeline).annotate(annotation);
				List<DigiKDResult> r = annotation.get(DigiKDAnnotations.KeyphrasesAnnotation.class);
				ArrayList<Color> colors = Utilities.generateColorArray(r.size());
				r.forEach(digiKDResult -> {
					Color c = colors.get(r.indexOf(digiKDResult));
					JsonObject key = new JsonObject();
					key.addProperty("name", digiKDResult.getKeyphrase());
					key.addProperty("value", digiKDResult.getScore());
					key.add("textStyle", new JsonObject());
					key.get("textStyle").getAsJsonObject().add("normal", new JsonObject());
					key.get("textStyle").getAsJsonObject().get("normal").getAsJsonObject().addProperty("color",
							"rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")");
					keywords.add(key);
				});

				// System.out.println(keywords.toString());
			}else if (lang.equals("fr")) {
				Annotation annotation = new Annotation(
						alltext.toString().replaceAll("#[A-Za-z]+", "").replaceAll("@[A-Za-z]+", ""));
				((StanfordCoreNLP) pipeline).annotate(annotation);
				List<DigiKDResult> r = annotation.get(DigiKDAnnotations.KeyphrasesAnnotation.class);
				ArrayList<Color> colors = Utilities.generateColorArray(r.size());
				r.forEach(digiKDResult -> {
					Color c = colors.get(r.indexOf(digiKDResult));
					JsonObject key = new JsonObject();
					key.addProperty("name", digiKDResult.getKeyphrase());
					key.addProperty("value", digiKDResult.getScore());
					key.add("textStyle", new JsonObject());
					key.get("textStyle").getAsJsonObject().add("normal", new JsonObject());
					key.get("textStyle").getAsJsonObject().get("normal").getAsJsonObject().addProperty("color",
							"rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")");
					keywords.add(key);
				});

				// System.out.println(keywords.toString());
			} else if (lang.equals("it")) {
				Annotation annotation = ((TintPipeline) pipeline)
						.runRaw(alltext.toString().replaceAll("#[A-Za-z]+", "").replaceAll("@[A-Za-z]+", ""));

				List<DigiKDResult> r = annotation.get(DigiKDAnnotations.KeyphrasesAnnotation.class);
				ArrayList<Color> colors = Utilities.generateColorArray(r.size());
				// Palette palette = PaletteManager.getInstance().generatePalette(r.size());
				r.forEach(digiKDResult -> {
					Color c = colors.get(r.indexOf(digiKDResult));
					JsonObject key = new JsonObject();
					key.addProperty("name", digiKDResult.getKeyphrase());
					key.addProperty("value", digiKDResult.getScore());
					key.add("textStyle", new JsonObject());
					key.get("textStyle").getAsJsonObject().add("normal", new JsonObject());
					key.get("textStyle").getAsJsonObject().get("normal").getAsJsonObject().addProperty("color",
							"rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")");
					keywords.add(key);
				});

				// System.out.println(keywords.toString());
			}

			if (globalStatsForHashTag.getNodes() > 0) {

				snapshotnet.add("net", globalStatsForHashTag.runColorizeEndForceAtals());
				snapshotnet.add("keywords", keywords);
				snapshotnet.add("messages", messages);
			}
			// System.out.println(snapshotnet.toString());

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		out.write(snapshotnet.toString());

	}

}
