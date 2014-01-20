package info.chees.patheutils.models;

public class Show {
	public String location;
	public String time;
	/** "IMAX 3D", "Voorpremière", etc. */
	public String type;
	public String url;
	
	public Show(String location, String time, String type, String url) {
		this.location = location;
		this.time = time;
		this.type = type;
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "l: " + location + " t: " + time + " T: " + type + " u: " + url;
	}
}
