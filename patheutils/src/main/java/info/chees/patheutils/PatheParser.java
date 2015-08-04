package info.chees.patheutils;

import info.chees.patheutils.models.Location;
import info.chees.patheutils.models.Movie;
import info.chees.patheutils.models.Show;

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
		String url = "http://www.pathe.nl/bioscoopagenda/denhaag?date=" + dateString;
		log.info(url);
		
		Connection connection = Jsoup.connect(url);
		connection.timeout(30000);
		Document doc = connection.get();
		
		List<Movie> result = new ArrayList<Movie>();
		
		for (Element e : doc.select(".schedule-movie")) {
			String title = e.select(".poster").get(0).attr("title");
			String thumbnail = e.select(".thumb img").get(0).attr("abs:src");
			Movie movie = new Movie(title, thumbnail);
			movie.locations = getLocations(e.select(".table-schedule").get(0));
			result.add(movie);
		}
		
		return result;
	}
	
	private static List<Location> getLocations(Element table) {
		List<Location> locations = new ArrayList<Location>();
		
		for (Element locEl : table.select("tr")) {
			Location location = new Location(locEl.select("th").text());
			location.shows = getShows(locEl.select("td").get(1));
			locations.add(location);
		}
		
		return locations;
	}
	
	private static List<Show> getShows(Element elem) {
		List<Show> shows = new ArrayList<Show>();
		
		for (Element a : elem.select("a")) {
			Elements spans = a.select("span");
			
			String time = spans.first().text();
			
			String type = null;
			if (spans.size() > 1)
				type = spans.get(1).text();
			
			String url = a.attr("abs:href");
			
			Show show = new Show(time, type, url);
			log.info("Show: " + show);
			shows.add(show);
		}
		return shows;
	}
}
