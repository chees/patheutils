package info.chees.patheutils;

import info.chees.patheutils.models.Movie;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PatheParser {
	private static final Logger log = Logger.getLogger(PatheParser.class.getName());
	
	public static List<Movie> getMovies(Date date) throws IOException {
		String dateString = new SimpleDateFormat("dd-MM-yyyy").format(date);
		//String dateString = "24-12-2013";
		String url = "http://www.pathe.nl/bioscoopagenda/denhaag/" + dateString;
		log.info(url);
		
		Connection connection = Jsoup.connect(url);
		connection.timeout(30000);
		Document doc = connection.get();
		
		Elements elems = doc.select(".movie-highlight.bioscoopagenda");
		List<Movie> result = new ArrayList<Movie>();
		for (Element e : elems) {
			String title = e.select("div.heading h3 a").get(0).text();
			String thumbnail = e.select("a img").get(0).attr("abs:src");
			result.add(new Movie(title, thumbnail));
		}
		
		return result;
	}
}
