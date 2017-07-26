package pubmedretriever.utils;

/**
 * Created by Megha Nagabhushan on 7/23/2017.
 */
public class MyPatterns {

    public static final String searchQuery = "(\"paraventricular\"[All Fields] AND (\"thalamic\"[All Fields] OR \"thalamus\"[All Fields])" +
            " AND (\"nucleus\"[All Fields] OR \"nuclei\"[All Fields]) NOT \"hypothalamus\"[All Fields] NOT \"hypothalamic\"[All Fields]) " +
            " OR (\"Paraventricular Thalamic Nucleus\"[All Fields] OR \"paraventricular nucleus of thalamus\"[All Fields] OR \"paraventricular nucleus of the thalamus\"[All Fields] OR \"paraventricular thalamus\"[All Fields])";

    public static final String pvtPattern = "(?i)(PVT|paraventricular ((nucleus of )(the )*)*(thalamus)|(paraventricular)* thalamic (nuclei|nucleus))";

    public static final String brainPattern = "(?i)(" +
            "hypothalamus|" +
            "cerebral cortex" +
            ")";
}
