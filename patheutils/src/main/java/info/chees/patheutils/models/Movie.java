package info.chees.patheutils.models;

import java.util.List;

public class Movie implements Comparable<Movie> {
	public String title;
	public String thumbnail;
	public String imdbId;
	public String imdbTitle;
	public float rating;
	public List<Location> locations;
	
	public Movie(String title, String thumbnail) {
		this.title = title;
		this.thumbnail = thumbnail;
	}
	
	@Override
	public String toString() {
		return title + "\n" + thumbnail + "\n" + imdbId + "\n" + imdbTitle + "\n" + rating + "\n";
	}

	@Override
	public int compareTo(Movie other) {
		if (other == null)
			return -1;
		int c = Float.compare(other.rating, this.rating);
		if (c == 0)
			return title.compareTo(other.title);
		return c;
	}
}
