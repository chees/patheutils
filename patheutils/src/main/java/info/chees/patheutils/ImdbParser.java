package info.chees.patheutils;

import info.chees.patheutils.models.ImdbFindResult;
import info.chees.patheutils.models.ImdbFindResultTitle;
import info.chees.patheutils.models.Movie;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ImdbParser {

	private static final Logger log = Logger.getLogger(ImdbParser.class.getName());
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static void fillImdbId(Movie movie) throws IOException {
		String title = cleanTitle(movie.title);
		String q = URLEncoder.encode(title, "UTF-8");
		URL url = new URL("http://www.imdb.com/xml/find?json=1&nr=1&tt=on&q=" + q);
		log.info(url.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);

		if (conn.getResponseCode() == 200) {
			ImdbFindResult res = mapper.readValue(conn.getInputStream(),
					ImdbFindResult.class);
			ImdbFindResultTitle popular = res.getFirstPopular();
			ImdbFindResultTitle exact = res.getFirstExact();
			ImdbFindResultTitle substring = res.getFirstSubstring();
			ImdbFindResultTitle approx = res.getFirstApprox();
			
			if (popular != null && exact != null) {
				if (popular.getYear() >= exact.getYear())
					movie.imdbId = popular.id;
				else
					movie.imdbId = exact.id;
			} else if (popular != null) {
				movie.imdbId = popular.id;
			} else if (exact != null) {
				movie.imdbId = exact.id;
			} else if (substring != null) {
				movie.imdbId = substring.id;
			} else if (approx != null) {
				movie.imdbId = approx.id;
			} else {
				log.warning("No usable id found:" + mapper.writeValueAsString(res));
			}
		}
	}
	
	private static String cleanTitle(String title) {
		if (title.endsWith("(OV)") || title.endsWith("(NL)")) {
			return title.substring(0, title.length() - 5);
		}
		return title;
	}

	public static void fillRating(Movie movie) throws IOException {
		if (movie.imdbId == null)
			return;
		String url = "http://www.imdb.com/title/" + movie.imdbId + "/";
		log.info(url);
		org.jsoup.Connection connection = Jsoup.connect(url);
		connection.request().header("Accept-Language", "en-US");
		connection.timeout(30000);
		Document doc = connection.get();
		
		Elements titles = doc.select("title");
		if (titles.size() < 1) {
			log.warning("title not found");
		} else {
			movie.imdbTitle = titles.get(0).text();
		}
		
		Elements ratings = doc.select(".star-box-giga-star");
		if (ratings.size() < 1) {
			log.warning("rating not found");
		} else {
			String rating = ratings.get(0).text();
			movie.rating = Float.parseFloat(rating);
		}
	}
}

