import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class parses movie synopsis documents and calculates document and term frequencies.
 * @author acelynchoi
 */
public class Corpus {

    // keeps track of document frequency for each word
    HashMap<String, Integer> docFreq;

    // all documents(corpus)
    ArrayList<String> documents;

    // each index corresponds to each documents and keeps track of term frequencies for each doc
    ArrayList<HashMap<String, Integer>> allTermFreq;


    public Corpus() {
        this.docFreq = new HashMap<String, Integer>();
        this.documents = new ArrayList<String>();
        this.allTermFreq = new ArrayList<HashMap<String, Integer>>();
    }

    /**
     * Updates Vector Space Model for the input movie synopsis
     * @param doc movie synopsis
     */
    public void parseDoc(String doc) {
         // add to corpus
        this.documents.add(doc);
         // allocate new space for this doc
        this.allTermFreq.add(new HashMap<String, Integer>());

         // all words in this doc
        for (String word: doc.split("\\s+")) {
            String filtered = word.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
            if (!(filtered.equalsIgnoreCase(""))) {
                 // if the word has not been encountered
                if (!(this.docFreq.containsKey(filtered))) {
                    this.docFreq.put(filtered, 1);
                 // if term has not been encountered in this current doc
                } else if (!(allTermFreq.get(allTermFreq.size() - 1).containsKey(filtered))) {
                    int prev = this.docFreq.get(filtered) + 1;
                    this.docFreq.put(filtered, prev);
                }
                 // updates term frequencies
                if (allTermFreq.get(allTermFreq.size() - 1) == null ||
                             !(allTermFreq.get(allTermFreq.size() - 1).containsKey(filtered))) {
                    allTermFreq.get(allTermFreq.size() - 1).put(filtered, 1);
                } else {
                    int prev = allTermFreq.get(allTermFreq.size() - 1).get(filtered) + 1;
                    allTermFreq.get(allTermFreq.size() - 1).replace(filtered, prev);
                }
            }
        }
    }

    public ArrayList<String> getDocuments() {
        ArrayList<String> copyDoc = new ArrayList<String>();

        for (String doc : documents) {
            copyDoc.add(doc);
        }
        return copyDoc;
    }
}
