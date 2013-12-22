package info.chees.patheutils.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbFindResultTitle {
	public String id;
	public String title;
	/*
	String name;
	String titleDescription;
	String episodeTitle;
	String description;
	*/
}