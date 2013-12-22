package info.chees.patheutils;

import info.chees.patheutils.models.ImdbFindResult;
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
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(30000);
		connection.setReadTimeout(30000);

		if (connection.getResponseCode() == 200) {
			ImdbFindResult res = mapper.readValue(connection.getInputStream(), ImdbFindResult.class);
			if (res.titlePopular != null && res.titlePopular.size() > 0) {
				movie.imdbId = res.titlePopular.get(0).id;
			} else if (res.titleExact != null && res.titleExact.size() > 0) {
				log.warning("titlePopular not found for movie: " + title);
				movie.imdbId = res.titleExact.get(0).id;
			} else if (res.titleApprox != null && res.titleApprox.size() > 0) {
				log.warning("titleApprox not found for movie: " + title);
				movie.imdbId = res.titleApprox.get(0).id;
			} else {
				log.warning(mapper.writeValueAsString(res));
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

