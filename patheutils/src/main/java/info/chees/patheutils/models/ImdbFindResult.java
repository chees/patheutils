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

	@JsonProperty("title_substring")
	public List<ImdbFindResultTitle> titleSubstring;
	
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
	
	public ImdbFindResultTitle getFirstSubstring() {
		return getFirstNonSerie(titleSubstring);
	}
	
	public ImdbFindResultTitle getFirstApprox() {
		return getFirstNonSerie(titleApprox);
	}
	
	private static ImdbFindResultTitle getFirstNonSerie(List<ImdbFindResultTitle> titles) {
		if (titles == null)
			return null;
		for (ImdbFindResultTitle t : titles) {
			// Only return non-TV titles
			if (t.description.indexOf(" TV ") == -1)
				return t;
			log.warning("skipping " + t.title + " " + t.description);
		}
		return null;
	}
}
