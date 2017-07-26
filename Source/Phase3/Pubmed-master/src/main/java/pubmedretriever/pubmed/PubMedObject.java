package pubmedretriever.pubmed;

/**
 * @author EG
 * POJO for each PubMed Publication
 * */
public class PubMedObject {

	private String id;
	private String abstractText;
	private boolean includesPVTPattern;
	private boolean includesBrainPattern;
	
	public boolean includesPVTPattern() {
		return includesPVTPattern;
	}
	public void setIncludesPVTPattern(boolean includesPVTPattern) {
		this.includesPVTPattern = includesPVTPattern;
	}
	public boolean includesBrainPattern() {
		return includesBrainPattern;
	}
	public void setIncludesBrainPattern(boolean includesBrainPattern) {
		this.includesBrainPattern = includesBrainPattern;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAbstractText() {
		return abstractText;
	}
	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}
	
}
