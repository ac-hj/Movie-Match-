
Name: Movie Match!

This project "Movie Match" is program written in Java that interacts with users to 
recommend movies based on their favorite movies. This program asks users for inputs (favorite genre
and movie)and incorporate those inputs to recommend users with Top 10 movies. If the user 
chooses to, users can get more recommendations based on their friends' favorite movies. 

This program heavily relies on web scraping IMDb using Jsoup to retrieve data and analyzes documents 
using Vector Space Model and cosine similarity. Once users enter necessary information, 
Top 45 movies in that genre are analyzed with cosine similarities calculated from their synopsis text 
retrieved from IMDb. I took in user input by adding "IMDb" to it and then googling it so that I can
directly access the IMDb page for the user input, which is user's favorite movie. This is why the user
has to input a movie specific enough, as google search may bring up very general results in IMDb, 
such as movie series description for "James Bond" instead of the IMDb page for a specific James
Bond movie. I also decided to use 14 genres listed in IMDb, excluding 'superhero,' because 
'superhero' genre page had a different format and I assumed that movies listed in that genre 
are very likely to be in other genres as well. 

Each of the documents are then compared with the "query document," which is the synopsis of user's favorite movie, 
and cosine similarities are calculated between them. Cosine similarities are then sorted from the 
greatest to least, and Top 10 of them are extracted to be in the recommendation list that will be 
printed for the user. 

When making more recommendations from users' friends' favorite movies, I integrated the concept
of membership closure, although the affiliation may not be as strong. Using this idea, I 
recommended more movies to the user by first finding Top 10 movies recommended for each of user's 
friends based on their favorite movies. Then, I iterated through the lists and recorded
movies that appear in all three friend's Top 10 lists. By making it such that
the movies have to appear in all three lists, I tried to make it more probable that the user will actually
be interested in those recommended movies, in an attempt to increase the accuracy of this program
overall. 

Side Note: I'm currently working on replicating this program using IMDb API. 
