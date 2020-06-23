import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class constructs VectorSpaceModel for all of the movie synopsis documents.
 * @author acelynchoi
 */
public class VectorSpaceModel {

    // all cosine similarity
    ArrayList<Double> cosineSimilarity;

    // movieNames
    ArrayList<String> movieNames;

    // vector space model
    ArrayList<HashMap<String, Double>> vector;

    // corpus of all documents
    Corpus corpus;


    public VectorSpaceModel(Corpus corpus) {
        this.vector = new ArrayList<HashMap<String, Double>>();
        this.movieNames = new ArrayList<String>();
        this.cosineSimilarity = new ArrayList<Double>();
        this.corpus = corpus;
    }

    /**
     * Calculates cosine similarities between query and all other docs
     * Ranks top 10 recommendations
     *
     * @param input favorite movie input from user
     * @return list of top 10 recommendations for user
     */
    public ArrayList<String> getTop10CosineSimilarities(int input) {
        ArrayList<String> answer = new ArrayList<String>();
        HashMap<Double, String> csMovie = new HashMap<Double, String>();

        for (int i = 0; i < this.vector.size(); i ++) {
            double cs = this.cosineSimilarityCalculator(this.vector.get(input),
                        this.vector.get(i));
            this.cosineSimilarity.add(cs);
            csMovie.put(cs, this.movieNames.get(i));
        }
        Collections.sort(this.cosineSimilarity);
        Collections.reverse(this.cosineSimilarity);

        for (int i = 0; i < 11; i ++) {
            String movieName = csMovie.get(this.cosineSimilarity.get(i));
            if (!(movieName.equalsIgnoreCase(this.movieNames.get(input)))) {
                answer.add(movieName);
            }
            if (answer.size() == 10) {
                break;
            }
        }
        return answer;
    }

    /**
     * Makes vectors for each doc
     */
    public void setVectors() {
        for (int i = 0; i < movieNames.size(); i ++) {
            vector.add(new HashMap<String, Double>());
        }
        for (int i = 0; i < this.corpus.allTermFreq.size(); i ++) {
            for (String word: this.corpus.docFreq.keySet()) {
                inverseFrequencyCalculatorAndUpdateVector(word, i);
            }
        }
    }

    /**
     * Retrieves document frequency for the word
     * @param word
     * @return document frequency for each word
     */
    private int documentFrequencyCalculator(String word) {
        if (!(this.corpus.docFreq.containsKey(word))) {
            return 0;
        } else {
            return this.corpus.docFreq.get(word);
        }
    }

    /**
     * Calculates inverse frequency for a given word and doc and updates vector
     * @param word input word
     * @param doc doc that the input word is in
     */
    private void inverseFrequencyCalculatorAndUpdateVector(String word, int doc) {
        Double idf = Math.log10((double)this.corpus.documents.size()
                    / documentFrequencyCalculator(word));
        double termFreq;
        if (!(this.corpus.allTermFreq.get(doc).containsKey(word))) {
            termFreq = 0;
        } else {
            termFreq = this.corpus.allTermFreq.get(doc).get(word);
        }
        this.vector.get(doc).put(word, termFreq * idf);
    }

    /**
     * Calculates cosine similarity between query vector and another doc
     * @param queryVector
     * @param indivVector
     * @return cosine similarity
     */
    double cosineSimilarityCalculator(HashMap<String, Double> queryVector
                , HashMap<String, Double> indivVector) {
        return dotProductCalc(queryVector, indivVector) / (magnitudeCalc(queryVector)
                    * magnitudeCalc(indivVector));
    }

    /**
     * Calculates magnitude for input vector
     * @param indivVector
     * @return magnitude
     */
    private double magnitudeCalc(HashMap<String, Double> indivVector) {
        double magnitude = 0;

        for (double weight : indivVector.values()) {
            magnitude += weight * weight;
        }
        return Math.sqrt(magnitude);
    }

    /**
     * Calculates dot product between two input vectors
     * @param queryVector
     * @param indivVector
     * @return dot product of the two vectors
     */
    private double dotProductCalc(HashMap<String, Double> queryVector,
                HashMap<String, Double> indivVector) {
        double product = 0;
        for (String term : queryVector.keySet()) {
            // since this will equal 0 if one vector does not have the term
            if (indivVector.containsKey(term)) {
                product += queryVector.get(term) * indivVector.get(term);
            }
        }
        return product;
    }

    public ArrayList<String> getMovieNames() {
        ArrayList<String> copyMovieNames = new ArrayList<String>();

        for (String movie : movieNames) {
            copyMovieNames.add(movie);
        }
        return copyMovieNames;
    }

}
