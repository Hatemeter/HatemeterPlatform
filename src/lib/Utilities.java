package lib;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletContext;

import com.google.gson.JsonObject;

import eu.fbk.dh.tint.runner.TintPipeline;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

public class Utilities {
	public static ArrayList<Color> generateColorArray(Integer n) {
		ArrayList<Color> colors = new ArrayList<>();
		Random rand = new Random();

		for (int i = 0; i < n; i++) {
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			Color randomColor = new Color(r, g, b);
			colors.add(randomColor);
		}

		return colors;
	}

	public static Twitter getTwitterObject(ServletContext context) {
		System.setProperty("twitter4j.http.httpClient", "twitter4j.WorkaroundLongTweetHttpClientImpl");

		ConcurrentLinkedQueue<JsonObject> keys = (ConcurrentLinkedQueue<JsonObject>) context.getAttribute("twKeys");
		JsonObject acceskeys = keys.poll();
		keys.add(acceskeys);

		String consumerKey = acceskeys.get("consumerKey").getAsString();
		String consumerSecretKey = acceskeys.get("consumerSecretKey").getAsString();

		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		if (acceskeys.has("accessToken")) {
			String accessToken = acceskeys.get("accessToken").getAsString();
			String accessTokenSecret = acceskeys.get("accessTokenSecret").getAsString();
			//System.out.println("Now Use user credintials " + consumerKey + " access Token "+accessToken);
			
			cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecretKey)
					.setApplicationOnlyAuthEnabled(true).setJSONStoreEnabled(true);
			 cb.setApplicationOnlyAuthEnabled(false);
             cb.setOAuthAccessToken(accessToken);
             cb.setOAuthAccessTokenSecret(accessTokenSecret);
			
		} else {
			//System.out.println("Now Use app credintials " + consumerKey);
			cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecretKey)
					.setApplicationOnlyAuthEnabled(true).setJSONStoreEnabled(true);

			ConfigurationBuilder cb2 = new ConfigurationBuilder();
			cb2.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecretKey)
					.setApplicationOnlyAuthEnabled(true);

			OAuth2Token token = null;

			try {
				token = new TwitterFactory(cb2.build()).getInstance().getOAuth2Token();
				cb.setOAuth2TokenType(token.getTokenType());
				cb.setOAuth2AccessToken(token.getAccessToken());
			} catch (Exception e) {
				e.printStackTrace();
			}
			// twitter4j auth end
		}
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter;
	}
}
