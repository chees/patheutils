package info.chees.patheutils.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbFindResultTitle {
	public String id;
	public String title;
	public String description;
	/*
	String name;
	String titleDescription;
	String episodeTitle;
	*/
	
	public int getYear() {
		try {
			// apparently this can also be "????"
			return Integer.parseInt(description.substring(0, 4));
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}