package wordnet;

import QAnsweringSystem.Lemmatization;
import org.apache.commons.io.FileUtils;
import rita.RiWordNet;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class WordNetMain {

    public static void main(String args[])
    {
        String word;
        String pos;
      RiWordNet wordnet = new RiWordNet("/Users/satheeshchandra/Desktop/KDM/WordNet-3.0");
        File folder = new File("src/mydataset");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
      if (file.isFile() && file.getName().endsWith(".txt")) {
                try {
                    String content = FileUtils.readFileToString(file);
                    String lemmatizedstring= QAnsweringSystem.Lemmatization.returnLemma(content);
                    StringTokenizer tokenizer = new StringTokenizer(lemmatizedstring);
                    while (tokenizer.hasMoreTokens()){
                    word = tokenizer.nextToken();
                        String[] poss = wordnet.getPos(word);
                        for (int j = 0; j < poss.length; j++) {
                            System.out.println("\n\nSynonyms for " + word //+ " (pos: " + poss[j] + ")"
                                     );
                            String[] synonyms = wordnet.getAllSynonyms(word,poss[j],1);
                            if(synonyms==null){}
                            else {
                            for (int x = 0; x < synonyms.length; x++) {
                                System.out.println(synonyms[i]);
                            }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


        // Demo finding a list of related words
        // X is Hypernym of Y if every Y is of type X
        // Hyponym is the inverse

//        String word = "obama";
//        String pos = wordnet.getBestPos(word);
//        System.out.println("\n\nHyponyms for " + word + ":");
//        String[] hyponyms = wordnet.getAllHyponyms(word, pos);
//        if (hyponyms==null){}
//        else {
//            for (int i = 0; i < hyponyms.length; i++) {
//                System.out.println(hyponyms[i]);
//            }
//        }

//        System.out.println("\n\nHypernyms for " + word + ":");
//        String[] hypernyms = wordnet.getAllHypernyms(word, pos);
//        for (int i = 0; i < hypernyms.length; i++) {
//            System.out.println(hypernyms[i]);
//        }


//        String start = "dog";
//        String end = "giraffe";
//        pos = wordnet.getBestPos(start);
//
//        // Wordnet can find relationships between words
//        System.out.println("\n\nRelationship between: " + start + " and " + end);
//        float dist = wordnet.getDistance(start,end,pos);
//        String[] parents = wordnet.getCommonParents(start, end, pos);
//        System.out.println(start + " and " + end + " are related by a distance of: " + dist);
//
//        // These words have common parents (hyponyms in this case)
//        System.out.println("Common parents: ");
//        if (parents != null) {
//            for (int i = 0; i < parents.length; i++) {
//                System.out.println(parents[i]);
//            }
//        }

        //System.out.println("\n\nHypernym Tree for " + start);
        //int[] ids = wordnet.getSenseIds(start,wordnet.NOUN);
        //wordnet.printHypernymTree(ids[0]);

    }
}

