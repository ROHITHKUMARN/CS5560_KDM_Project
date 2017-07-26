package pubmedretriever.pubmed;

import pubmedretriever.utils.FileUtils;
import pubmedretriever.utils.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author EG
 * This class is responsible from the processes about the PubMed publications 
 * */
public class PubMedProcessor {
	
	private PubMedConnector connector;
	
	public PubMedProcessor(){
		connector = new PubMedConnector();
	}
	
	/**
	 * This method creates a list of PubMed objects after getting the results of the PVT query
	 * @return List<PubMedObject> : List of pubmed objects
	 * */
	public List<PubMedObject> getPubMedObjectList(String fromFile){

		List<PubMedObject> pubMedObjectList;
		
		if (fromFile == null || fromFile.equals("true")){
			pubMedObjectList = FileUtils.readPubMedListFromFile();
		} else {
			pubMedObjectList = retrievePubMedList();
			
			// Write the abstracts of the publications to the output
			FileUtils.writeAbstractsToFile(pubMedObjectList);
		}
		
		pubMedObjectList = getComparedPubMedList(pubMedObjectList);
		
		return pubMedObjectList;
	}

	
	/**
	 * This method creates a list of PubMed objects after getting the results of the PVT query
	 * @return List<PubMedObject> : List of pubmed objects
	 * */
	public List<PubMedObject> retrievePubMedList() {
		List<PubMedObject> pubMedObjectList = new ArrayList<PubMedObject>();
		
		List<String> pubMedIds = connector.search(Patterns.searchQuery);
		
		for (String id : pubMedIds){
			PubMedObject pubMed = new PubMedObject();
			pubMed.setId(id);
			pubMed.setAbstractText(connector.fetch(id));
			pubMedObjectList.add(pubMed);
		}
		return pubMedObjectList;
	}

	
	public List<PubMedObject> getComparedPubMedList(List<PubMedObject> pubMedObjectList){
		
		for (PubMedObject pubMedObject : pubMedObjectList){
			
			String abstractText = pubMedObject.getAbstractText();
			if (abstractText != null){
				
				// Abstract vs PVT Patterns
				Pattern p = Pattern.compile(Patterns.pvtPattern);
				Matcher matcher = p.matcher(abstractText.replace("\n", " "));
				pubMedObject.setIncludesPVTPattern(matcher.find());
				
				// Abstract vs Brain Patterns
				p = Pattern.compile(Patterns.brainPattern);
				matcher = p. matcher(abstractText.replace("\n", " "));
				pubMedObject.setIncludesBrainPattern(matcher.find());
			}
			
		}
		
		return pubMedObjectList;
	}

}
