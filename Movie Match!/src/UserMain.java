import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class interacts with the user.
 * @author acelynchoi
 */
public class UserMain {

    public static void main(String[] args) {
        // Initializes Scanner for user input and PrintWriter to print out the answer.
        PrintWriter writer = new PrintWriter(System.out, true);
        writer.println("⭑･ﾟﾟ･*:༅｡.｡༅:*ﾟ:*:✼✿　　✿✼:*ﾟ:༅｡.｡༅:*･ﾟﾟ･⭑⭑･ﾟﾟ･*:༅｡.｡༅:*ﾟ:*:✼✿　　✿✼:*ﾟ:༅｡.｡༅:"
                    + "*･ﾟﾟ･⭑˚ ⭑･ﾟﾟ･*:༅｡.｡༅:*ﾟ:*:✼✿　　✿✼:*ﾟ:༅｡.｡༅:*･ﾟﾟ･⭑　⭑･ﾟﾟ･*:༅｡.｡༅:*ﾟ");
        writer.println("\n Welcome to interactive Movie Match •ᴗ•! "
                    + "\n Press 0 to read the user guide or press 1 to use start getting "
                    + "movie recommendations right away!");
        // Asks to enter a valid input until it is given.
        boolean validInput = false;
        int ques = 0;
        Scanner sc;
        do {
            try {
                sc = new Scanner(System.in);
                ques = sc.nextInt();
                validInput = true;
                if (ques < 0 || ques > 1) {
                    writer.println("Please try again!");
                    validInput = false;
                } else {
                    options(ques);
                    sc.close();
                }
            } catch (InputMismatchException e) {
                writer.println("Please enter a *valid* input.");
            }
        } while (!(validInput));
        writer.close();
    }

    // assumptions about territories
    // do we need to talk about which are possible inputs that the user can put in?
    private static void options(int ques) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out, true);
        switch (ques) {
            case 0:
                out.println("\n Welcome again! To use Movie Match, you will first be asked to"
                            + " choose a genre from which you would like to watch a movie."
                            + "\n This genre can be of the following 14 choices: "
                            + "\n Comedy              Sci-fi"
                            + "\n Horror              Romance"
                            + "\n Action              Thriller"
                            + "\n Drama               Mystery"
                            + "\n Crime               Animation"
                            + "\n Adventure           Fantasy"
                            + "\n Comedy-romance      Action-comedy"
                            + "\n \n Next, you will asked to enter your favorite movie of any "
                            + "genre (don't need to be of the 14 listed above)."
                            + "\n You will also be asked if you want more recommendations based "
                            + "on 3 of your friends' favorite movies."
                            + "\n \n Once all of the information has been entered, we will print "
                            + "out top 10 movies."
                            + "\n If you indicated that you wanted more recommendations based on "
                            + "your friends' preferences,"
                            + "\n you will also receive more recommendations soon."
                            + "\n Let's get started!(ﾉ◕ヮ◕)ﾉ*:・ﾟ✧");
                out.println("************************************************"
                            + "**********************************************");
            case 1:
                out.println("\n Which genre would you like to explore today?");
                out.println("\n Please enter ONE of the following "
                            + "(e.g. Sci-fi). "
                            + "\n Comedy              Sci-fi"
                            + "\n Horror              Romance"
                            + "\n Action              Thriller"
                            + "\n Drama               Mystery"
                            + "\n Crime               Animation"
                            + "\n Adventure           Fantasy"
                            + "\n Comedy-romance      Action-comedy");
                String userGenre = in.nextLine();
                out.println("\n What is your favorite movie?");
                String userMovie = in.nextLine();
                out.println(" Would you like to get more recommendations based on your friends' "
                            + "favorite movies?"
                            + "\n Note that this feature is only used to give more recommendations"
                            + " in addition to the top 10 recommendations based on your preference."
                            + "\n Enter \"No thanks\" if you would not like to you this"
                            + " feature. ");
                String userAnswer = in.nextLine();

                Parser parser = new Parser();

                out.println(" Printing out your movie recommendations..... Please wait for ~ "
                            + "2 mins.");
                parser.getSpecificInputMoviePages(userMovie);
                parser.getGenrePage(userGenre);
                out.println("*:･ﾟ✧*:･ﾟ✧ Your Top 10 recommendations  *:･ﾟ✧*:･ﾟ✧");
                for (String movie : parser.answer) {
                    out.println(" ✪  " + movie);
                }
                if (!(userAnswer.equals("No thanks"))) {
                    out.println("⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆"
                                + "⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆⋆");
                    ArrayList<String> friendRecs = new ArrayList<String>();
                    Parser parser1 = new Parser();
                    out.println("\n What is your friend 1's favorite movie?");
                    String friend1 = in.nextLine();
                    out.println(" Gathering data for friend 1.......");
                    parser1.getSpecificInputMoviePages(friend1);
                    parser1.getGenrePage(userGenre);
                    ArrayList<String> f1 = parser1.answer;

                    Parser parser2 = new Parser();
                    out.println("\n What is your friend 2's favorite movie?");
                    String friend2 = in.nextLine();
                    out.println(" Gathering data for friend 2.......");
                    parser2.getSpecificInputMoviePages(friend2);
                    parser2.getGenrePage(userGenre);
                    ArrayList<String> f2 = parser2.answer;

                    Parser parser3 = new Parser();
                    out.println("\n What is your friend 3's favorite movie?");
                    String friend3 = in.nextLine();
                    out.println(" Gathering data for friend 3.......");
                    parser3.getSpecificInputMoviePages(friend3);
                    parser3.getGenrePage(userGenre);
                    ArrayList<String> f3 = parser3.answer;

                    if (friend1.equals(friend2) && friend2.equals(friend3)) {
                        friendRecs.add(friend1);
                    }
                    for (String movie : f1) {
                        if (f2.contains(movie) && f3.contains(movie)) {
                            friendRecs.add(movie);
                        }
                    }

                    if (friendRecs.size() == 0) {
                        out.println(" No recommendations available!");
                    } else {
                        out.println("\n Here are your recommendations: ");
                        for (String rec: friendRecs) {
                            out.println(" ⦿ " + rec);
                        }
                    }
                }
                out.println("꘎♡━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                            + "━━━━━━━━━━━♡꘎");
                out.println(" Thanks for using! Come back again ʕ•́ᴥ•̀ʔ");
                break;
            default:
                break;
        }
        in.close();
        out.close();
    }
}
