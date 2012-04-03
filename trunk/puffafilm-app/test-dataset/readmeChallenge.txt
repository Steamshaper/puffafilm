
=========================
Challenge SII (2011/2012)
=========================

--------
Versione
--------

Versione 1.0 (Dicembre 2011)

-----------
Descrizione
-----------

Dataset per la challenge del corso di Sistemi Intelligenti per Internet.

L'obiettivo è prevedere il rating degli utenti per i film.

Il rating è un valore che varia da 0 a 5 (con passo di 0,5).
I possibili valori sono i seguenti 10:
0,5
1
1,5
2
2,5
3
3,5
4
4,5
5

In fase di valutazione verranno testati gli algoritmi su un dataset sconosciuto attraverso la metrica MAE (Mean Average Error), mediando tutte le differenza tra il valore predetto e quello reale.

I dati dei film sono stati estratti dai siti IMDb and Rotten Tomatoes.
Le feature sono le seguenti:
   - Titoli (in Spangnolo!)
   - IMDb movie ids
   - IMDb picture URLs
   - Rotten Tomatoes movie ids
   - Rotten Tomatoes picture URLs
   - Rotten Tomatoes (all/top) critics' ratings, avg. scores, numbers of reviews/fresh_scores/rotten_scores
   - Rotten Tomatoes audience' avg. ratings, number of ratings, avg. scores

--------------------
Statistiche sui dati
--------------------

    2113 users
   10197 movies
   
      20 movie genres
   20809 movie genre assignments
         avg. 2.040 genres per movie

    4060 directors
   95321 actors
         avg. 22.778 actors per movie
      72 countries

   10197 country assignments
         avg. 1.000 countries per movie
   47899 location assignments
         avg. 5.350 locations per movie

   13222 tags
   

----------------
Formato dei dati
----------------

   I dati sono formattati con una entry per linea come segue:

   * movies.dat
   
        id \t title \t imdbID \t spanishTitle \t imdbPictureURL \t year \t rtID \t rtAllCriticsRating \t rtAllCriticsNumReviews \t rtAllCriticsNumFresh \t rtAllCriticsNumRotten \t rtAllCriticsScore \t rtTopCriticsRating \t rtTopCriticsNumReviews \t rtTopCriticsNumFresh \t rtTopCriticsNumRotten \t rtTopCriticsScore \t rtAudienceRating \t rtAudienceNumRatings \t rtAudienceScore \t rtPictureURL

        Esempio:
        1	Toy story	0114709	Toy story (juguetes)	http://ia.media-imdb.com/images/M/MV5BMTMwNDU0NTY2Nl5BMl5BanBnXkFtZTcwOTUxOTM5Mw@@._V1._SX214_CR0,0,214,314_.jpg	1995	toy_story	9	73	73	0	100	8.5	17	17	0	100	3.7	102338	81	http://content7.flixster.com/movie/10/93/63/10936393_det.jpg

   * movie_genres.dat
   
        movieID	\t genre

        Esempio:
        1	Adventure

   * movie_directors.dat

        movieID \t directorID \t directorName

        Esempio:
        1	john_lasseter	John Lasseter
   
   * movie_actors.dat

        movieID \t actorID \t actorName \t ranking

        Esempio:
        1	annie_potts	Annie Potts	10
   
   * movie_countries.dat

        movieID \t country

        Esempio:
        1	USA

   * movie_locations.dat

        movieID \t location1 \t location2 \t location3 \t location4

        Esempio:
        2	Canada	British Columbia	Vancouver
   
   * tags.dat

        id \t value

        Esempio:
        1	earth

   * movie_tags.dat

        movieID \t tagID \t tagWeight

        Esempio:
        1	13	3
   
   * user_taggedmovies.dat

        userID \t movieID  \t tagID  \t timestamp

        Esempio:
        75	353	5290	1162160415000
        
   
   * user_ratedmovies.dat

        userID \t movieID \t rating \t timestamp

        Esempio:
        75	3	1	1162160236000


--------   
Contatti
--------

http://www.dia.uniroma3.it/~sii

sii@dia.uniroma3.it