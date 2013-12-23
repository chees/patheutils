package info.chees.patheutils;

import info.chees.patheutils.models.Movie;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PatheParser {
	public static List<Movie> getMovies() throws IOException {
		//String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String date = "24-12-2013";
		Connection connection = Jsoup.connect("http://www.pathe.nl/bioscoopagenda/denhaag/" + date);
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
