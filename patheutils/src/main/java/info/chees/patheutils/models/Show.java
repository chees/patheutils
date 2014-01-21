package info.chees.patheutils.models;

public class Show {
	public String time;
	/** "IMAX 3D", "Voorpremière", etc. */
	public String type;
	public String url;
	
	public Show(String time, String type, String url) {
		this.time = time;
		this.type = type;
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "t: " + time + " T: " + type + " u: " + url;
	}
}
