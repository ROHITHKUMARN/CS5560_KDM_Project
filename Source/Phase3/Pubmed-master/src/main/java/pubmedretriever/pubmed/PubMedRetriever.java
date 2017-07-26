package pubmedretriever.pubmed;

import pubmedretriever.utils.FileUtils;

import java.util.List;


public class PubMedRetriever {

	public static void main(String[] args) {
		
		PubMedProcessor processor = new PubMedProcessor();

		//String input = args[0];
		
		// Return the list of PubMed Publications
		//List<PubMedObject> pubMedObjectList = processor.getPubMedObjectList(args[0]);
		List<PubMedObject> pubMedObjectList = processor.getPubMedObjectList("true");
		
		// Write the pattern evaluation of the publications to the output
		FileUtils.writePatternResultsToFile(pubMedObjectList);
		
		System.out.println("Please check PublicationList.csv under output folder..");
	}

}
