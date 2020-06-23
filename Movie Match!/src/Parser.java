import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Retrieves and parses data from IMDb.
 * @author acelynchoi
 *
 */
public class Parser {

    private String baseUrl;
    private Document currentDoc;
    // maps movies to their page urls
    private HashMap<String, String> movieUrls;
    private int input;
    private String movieName;
    ArrayList<String> answer;
    Corpus corpus;
    VectorSpaceModel model;

        /**
         * Constructor that initializes the base URL and loads the document produced from that URL
         * Leads to the home page of CIA World Factbook
         */
    public Parser() {
        // keeps track of all of the movie
        this.corpus = new Corpus();
        this.model = new VectorSpaceModel(corpus);
        this.movieUrls = new HashMap<String, String>();
        this.baseUrl = "https://www.imdb.com/feature/genre/";

        try {
            this.currentDoc = Jsoup.connect(this.baseUrl).get();
        } catch (IOException e) {
            System.out.println("Could not retrieve the website :(");
        } catch (Exception e) {
            System.out.println();
            System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                        + "input is specific enough, genre is valid, etc.)");
            System.out.println();
            throw new IllegalArgumentException();
        }
        this.answer = new ArrayList<String>();
    }

    /**
     * Connects to input genre page from baseUrl
     * @param genre user input
     */
    public void getGenrePage(String genre) {
        genre = genre.toLowerCase();

        String genreUrl = "";
        try {
            Elements ninjaElements = this.currentDoc.select("div.pagecontent")
                        .select("div.ninja_left");
            for (Element row : ninjaElements) {
                for (Element child : row.select("div.widget_image")) {
                    String getGenre = child.select("img.pri_image").attr("title").toLowerCase();
                    if (genre.equals(getGenre)) {
                        genreUrl = child.select("a").attr("href");
                        break;
                    }
                }
                if (!(genreUrl.equals(""))) {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println();
            System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                        + "input is specific enough, genre is valid, etc.)");
            System.out.println();
            throw new IllegalArgumentException();
        }


        // when input genre is not valid
        if (genreUrl.equals("")) {
            System.out.println("Please restart the program and enter a valid genre.");
            return;
        }

        try {
            this.currentDoc = Jsoup.connect(genreUrl).get();
        } catch (IOException e) {
            System.out.println("Could not retrieve the website :(");
        } catch (Exception e) {
            System.out.println();
            System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                        + "input is specific enough, genre is valid, etc.)");
            System.out.println();
            throw new IllegalArgumentException();
        }
        getDataFromMoviePage();
    }

    /**
     * Connects to "Feature Films" page from genre page
     * @return url to "Feature Films" page
     */
    private String getMoviePageFromGenrePage() {
        String movieUrl = "";
        try {
            Element tbody = this.currentDoc.select("div#wrapper").select("div#sidebar")
                        .select("tbody").get(0);
            for (Element body : tbody.children().select("a")) {
                if (body.text().equals("Feature Films")) {
                    movieUrl = body.select("a").attr("abs:href");

                }
            }
        } catch (Exception e) {
            System.out.println();
            System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                        + "input is specific enough, genre is valid, etc.)");
            System.out.println();
            throw new IllegalArgumentException();
        }

        return movieUrl;
    }

    /**
     * Gets all urls of top 45 movies from "Feature Films" page
     */
    private void getDataFromMoviePage() {
        try {
            this.currentDoc = Jsoup.connect(getMoviePageFromGenrePage()).get();
        } catch (IOException e) {
            System.out.println("Could not retrieve the website :(");
        } catch (Exception e) {
            System.out.println();
            System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                        + "input is specific enough, genre is valid, etc.)");
            System.out.println();
            throw new IllegalArgumentException();
        }

        Elements movieList = this.currentDoc.select("div.lister-list")
                    .select("div.lister-item-content");

        Queue<Element> movies = new LinkedList<Element>();
        // iterator
        for (Element single : movieList) {
            movies.add(single);
        }

        int ctr = 1;
        try {
            while (!(movies.isEmpty())) {
                Element singleMovie = movies.remove();
                this.movieUrls.put(singleMovie.select("a").get(0).text(),
                            singleMovie.select("a").attr("abs:href"));

                if (ctr % 50 == 0) {
                    try {
                        String nextPageUrl = this.currentDoc.select("div#wrapper")
                                    .select("div.desc").select("a").attr("abs:href");
                        this.currentDoc = Jsoup.connect(nextPageUrl).get();
                    } catch (IOException e) {
                        System.out.println("Could not retrieve the website :(");
                    } catch (Exception e) {
                        System.out.println();
                        System.out.println("ERROR: Please make sure your input is valid (i.e. your"
                                    + " movie input is specific enough, genre is valid, etc.)");
                        System.out.println();
                        throw new IllegalArgumentException();
                    }

                    Elements moreMovies = this.currentDoc.select("div.lister-list")
                        .select("div.lister-item-content");
                    for (Element movie :  moreMovies) {
                        movies.add(movie);
                    }
                }
                if (ctr == 45) {
                    break;
                }
                ctr ++;
            }
        } catch (Exception e) {
            System.out.println();
            System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                        + "input is specific enough, genre is valid, etc.)");
            System.out.println();
            throw new IllegalArgumentException();
        }

        getMovieSynopsis();
    }

    /**
     * Retrieves synopsis of all 45 movies if they exist (if synopsis doesn't
     * exist for one of user inputs' movies, then we terminate the program and
     * Print out that we cannot recommend any movies with given inputs.
     */
    private void getMovieSynopsis() {
        for (String movie : this.movieUrls.keySet()) {
            try {
                this.currentDoc = Jsoup.connect(this.movieUrls.get(movie)).get();
            } catch (IOException e) {
                System.out.println("Could not retrieve the website :(");
            } catch (Exception e) {
                System.out.println();
                System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                            + "input is specific enough, genre is valid, etc.)");
                System.out.println();
                throw new IllegalArgumentException();
            }

            String url = "";
            try {
                for (Element quickElement : this.currentDoc.select("div.quicklinkSection")
                            .select("div.quicklinkGroup")) {
                    if (quickElement.select("div.quicklinkSectionHeader").text()
                                .equals("STORYLINE")) {
                        url = quickElement.select("div.quicklinkSectionItem")
                                    .get(2).select("a").attr("abs:href");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                            + "input is specific enough, genre is valid, etc.)");
                System.out.println();
                throw new IllegalArgumentException();
            }

            getSynopsisString(url, this.model, this.corpus, movie);
            if (movie.equals(this.movieName)) {
                this.input = corpus.documents.size() -1;
            }
        }

        if (!(this.model.movieNames.contains(this.movieName))) {
            System.out.println("Sorry we cannot recommend any movies with your input. "
                        + "Please restart the program and try again!"
                        + "\n This may have occured either because "
                        + "there was no synopsis for your input or your friend's.");
            return;
        }
        this.model.setVectors();
        this.answer = this.model.getTop10CosineSimilarities(this.input);

    }

    /**
     * Sends synopsis for each movie to VectorSpaceModel
     * @param url
     * @param model
     * @param movie
     */
    private void getSynopsisString(String url, VectorSpaceModel model, Corpus corpus, String movie) {
        try {
            this.currentDoc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("Could not retrieve the website :(");
        } catch (Exception e) {
            System.out.println();
            System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                        + "input is specific enough, genre is valid, etc.)");
            System.out.println();
            throw new IllegalArgumentException();
        }
        String text = "";
        try {
            if (this.currentDoc.select("ul.ipl-zebra-list").size() != 0) {
                text = this.currentDoc.select("ul.ipl-zebra-list").get(1)
                            .select("li.ipl-zebra-list__item").text();
                if (!(text.contains("It looks like we don't have a Synopsis for "
                            + "this title yet."))) {
                    this.corpus.parseDoc(text);
                    this.model.movieNames.add(movie);
                }
            }
        } catch (Exception e) {
            System.out.println();
            System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                        + "input is specific enough, genre is valid, etc.)");
            System.out.println();
            throw new IllegalArgumentException();
        }
    }

    /**
     * User input for "favorite movie" query
     * Google searches the movie and retrieves the first result (assuming that searched query
     * was specific enough - will exit the program otherwise)
     * @param movies
     */
    public void getSpecificInputMoviePages(String movies) {
        this.movieName = movies;
        // start at google
        Document movieDoc;
        String movie = "";

        try {
            movieDoc = Jsoup.connect("https://www.google.com/search?q=" + movies + "imdb").get();
            movie = movieDoc.select("div.r").select("a").attr("abs:href");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println();
            System.out.println("ERROR: Please make sure your input is valid (i.e. your movie "
                        + "input is specific enough, genre is valid, etc.)");
            System.out.println();
            throw new IllegalArgumentException();
        }
        this.movieUrls.put(movies, movie);
    }
}
