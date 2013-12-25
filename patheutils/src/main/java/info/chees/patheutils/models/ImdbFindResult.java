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
		if (titlePopular == null || titlePopular.size() < 1) {
			log.warning("titlePopular not found");
			return null;
		}
		return titlePopular.get(0);
	}
	
	public ImdbFindResultTitle getFirstExact() {
		if (titleExact == null || titleExact.size() < 1) {
			log.warning("titleExact not found");
			return null;
		}
		return titleExact.get(0);
	}
	
	public ImdbFindResultTitle getFirstApprox() {
		if (titleApprox == null || titleApprox.size() < 1)
			return null;
		return titleApprox.get(0);
	}
}
