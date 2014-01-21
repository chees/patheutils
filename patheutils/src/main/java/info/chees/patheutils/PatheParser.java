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
		String url = "http://www.pathe.nl/bioscoopagenda/denhaag/" + dateString;
		log.info(url);
		
		Connection connection = Jsoup.connect(url);
		connection.timeout(30000);
		Document doc = connection.get();
		
		List<Movie> result = new ArrayList<Movie>();
		
		for (Element e : doc.select(".movie-highlight.bioscoopagenda")) {
			String title = e.select("div.heading h3 a").get(0).text();
			String thumbnail = e.select("a img").get(0).attr("abs:src");
			Movie movie = new Movie(title, thumbnail);
			movie.locations = getLocations(e.nextElementSibling());
			result.add(movie);
		}
		
		return result;
	}
	
	private static List<Location> getLocations(Element table) {
		List<Location> locations = new ArrayList<Location>();
		
		for (Element locEl : table.select("tr table tr")) {
			Location location = new Location(locEl.select("th a").get(0).ownText());
			location.shows = getShows(locEl.select("td a"));
			locations.add(location);
		}
		
		return locations;
	}
	
	private static List<Show> getShows(Elements elems) {
		List<Show> shows = new ArrayList<Show>();
		for (Element a : elems) {
			String[] timeSplit = a.ownText().split(" ", 2);
			String time = timeSplit[0];
			
			String type = null;
			if (timeSplit.length > 1)
				type = timeSplit[1];
			else if (a.children().size() > 0)
				type = a.child(0).text();
			
			String href = a.attr("href");
			
			String url = null;
			// For some shows you can't get tickets online:
			if (href.startsWith("javascript:openPopup("))
				url = "http://www.pathe.nl" + href.substring(22, href.length() - 2);
			
			Show show = new Show(time, type, url);
			log.info("Show: " + show);
			shows.add(show);
		}
		return shows;
	}
}
