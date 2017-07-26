package pubmedretriever.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import pubmedretriever.pubmed.PubMedObject;

public class FileUtils {

	private static final String abstractFilePath = "./abstracts/";
	private static final String publicationListFilePath = "./output/PublicationList.csv";
	
	public static void writeAbstractsToFile(List<PubMedObject> pubMedObjectList){
		
		File outputFile;
		BufferedWriter writer;
		
		try {
			
			for (PubMedObject pubMed : pubMedObjectList){
				
				outputFile = new File(abstractFilePath + pubMed.getId() + ".txt");
				
				if (!outputFile.exists()){
					writer = new BufferedWriter(new FileWriter(outputFile));				
					
					writer.write(pubMed.getAbstractText());
					
					writer.close();	
				}
				
			}

		
		} catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	public static void writePatternResultsToFile(List<PubMedObject> pubMedObjectList){
		
		File outputFile;
		BufferedWriter writer;
		
		try {
			
			outputFile = new File(publicationListFilePath);
			writer = new BufferedWriter(new FileWriter(outputFile));	
			
			writer.append("PUBLICATION ID,INCLUDES PVT PATTERNS, INCLUDES BRAIN PATTERNS\n");
			for (PubMedObject pubMed : pubMedObjectList){
			
				writer.append(pubMed.getId() + "," + pubMed.includesPVTPattern() + "," + pubMed.includesBrainPattern() + "\n");
				
			}
			
			writer.close();	
		
		} catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	public static List<PubMedObject> readPubMedListFromFile(){

		List<PubMedObject> pubMedList = new ArrayList<PubMedObject>();
		
		try {
			
			File folder = new File(abstractFilePath);
			File[] listOfFiles = folder.listFiles();

			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			        
			    	String fileName = listOfFiles[i].getName();
			    	 
			    	PubMedObject pubMed = new PubMedObject();
			    	pubMed.setId(fileName.replace(".txt", ""));
			    	
			    	Path path = FileSystems.getDefault().getPath(abstractFilePath, fileName);
			        String abstractText = new String (Files.readAllBytes(path), StandardCharsets.UTF_8 );
					pubMed.setAbstractText(abstractText);
					
					pubMedList.add(pubMed);
			      } 
			    }
				
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return pubMedList;
		
	}
	
}
