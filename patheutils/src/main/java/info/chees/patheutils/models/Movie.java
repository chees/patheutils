package info.chees.patheutils.models;

public class Movie implements Comparable<Movie> {
	public String title;
	public String thumbnail;
	public String imdbId;
	public String imdbTitle;
	public float rating;
	
	public Movie(String title, String thumbnail) {
		this.title = title;
		this.thumbnail = thumbnail;
	}
	
	@Override
	public String toString() {
		return title + "\n" + imdbId + "\n" + imdbTitle + "\n" + rating + "\n";
	}

	@Override
	public int compareTo(Movie other) {
		if (other == null)
			return -1;
		return Float.compare(other.rating, this.rating);
	}
}
