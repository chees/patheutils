package info.chees.patheutils.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbFindResult {
	@JsonProperty("title_popular")
	public List<ImdbFindResultTitle> titlePopular;

	@JsonProperty("title_exact")
	public List<ImdbFindResultTitle> titleExact;

	@JsonProperty("title_approx")
	public List<ImdbFindResultTitle> titleApprox;
}
