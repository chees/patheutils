package info.chees.patheutils.models;

import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbFindResult {
	
	private static final Logger log = Logger.getLogger(ImdbFindResult.class.getName());
	
	@JsonProperty("title_popular")
	public List<ImdbFindResultTitle> titlePopular;

	@JsonProperty("title_exact")
	public List<ImdbFindResultTitle> titleExact;

	@JsonProperty("title_approx")
	public List<ImdbFindResultTitle> titleApprox;

	public ImdbFindResultTitle getFirstPopular() {
		ImdbFindResultTitle t = getFirstNonSerie(titlePopular);
		if (t == null)
			log.warning("titlePopular not found");
		return t;
	}
	
	public ImdbFindResultTitle getFirstExact() {
		ImdbFindResultTitle t = getFirstNonSerie(titleExact);
		if (t == null)
			log.warning("titleExact not found");
		return t;
	}
	
	public ImdbFindResultTitle getFirstApprox() {
		return getFirstNonSerie(titleApprox);
	}
	
	private static ImdbFindResultTitle getFirstNonSerie(List<ImdbFindResultTitle> titles) {
		if (titles == null)
			return null;
		for (ImdbFindResultTitle t : titles) {
			// Movies have this format: "2013, Peter Jackson"
			// Or this: "2013/I, Chris Buck..."
			// Others don't have the comma: "1999 TV series documentary"
			// Ahw yeah, robust scraping ftw
			String[] words = t.description.split(" ");
			if (words.length > 0 && words[0].endsWith(","))
				return t;
			log.warning("skipping " + t.title + " " + t.description);
		}
		return null;
	}
}
