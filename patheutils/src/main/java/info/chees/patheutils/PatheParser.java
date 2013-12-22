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
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		Connection connection = Jsoup.connect("http://www.pathe.nl/bioscoopagenda/denhaag/" + date);
		connection.timeout(30000);
		Document doc = connection.get();
		Elements movies = doc.select("div.heading h3 a");
		List<Movie> result = new ArrayList<Movie>();
		for (Element m : movies) {
			result.add(new Movie(m.text()));
		}
		return result;
	}
}
