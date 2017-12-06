import java.util.*;
import javax.jdo.*;

@javax.jdo.annotations.PersistenceCapable

public class Movie {
	
	String title;
	int releaseYear;

	Studio studio; // The studio that made this movie
	Director director; // The director of this movie

	HashSet<Actor> actors = new HashSet<Actor>();
	             // The set of actors who acted in this movie


	public String toString() {
		return title+" "+releaseYear;
	}

	public static Collection<Movie> sameTitle(Query q) {

	/* Returns the collection of all movies each of which has at least
	   one other movie with the same title. 
	   Sort the result by (Movie.title, Movie.releaseYear).

		Set Form		
		Bound variable: mov: Movie
		{ m: Movie |  m.title == mov.title &&
					  m.releaseYear != mov.releaseYear }

	*/
	
		q.setClass(Movie.class);
		
		q.declareVariables("Movie m");
		
		q.setFilter("this.title == m.title &&" +
					"this.releaseYear != m.releaseYear" );
		
		q.setOrdering("this.title ascending, this.releaseYear ascending");
		
		return (Collection<Movie>) q.execute();

	}

	public static Collection<Movie> studioActors(String sName, int x, Query q) {

	/* Returns the collection of all movies "m" such that "m" was made by the studio with
	   the name "sName" and had at least "x" actors acting in it.
	   Sort the result by (Movie.releaseYear, Movie.title).
	   
	   	Set Form		
		Parameters: sName: String, x: int
		{ m: Movie |  m.studio.name == sName &&
					  m.actors.size() >= x }
					  
	*/

		q.setClass(Movie.class);
		q.declareParameters("String studioName, int xActors");
		
		q.setFilter("this.studio.name == studioName &&" +
					"this.actors.size() >= xActors");
		
		q.setOrdering("this.releaseYear ascending, this.title ascending");
		
		return (Collection<Movie>) q.execute(sName, x);

	}

	public static Collection<Movie> madeByDirectorBetweenTheseYears(Director d, int y1, int y2, Query q) {

	/* Returns the collection of all movies made by the director "d" 
	   between the years "y1" and "y2", inclusive. It is assumed y1 <= y2.
	   Sort the result by (Movie.title, Movie.releaseYear).

		
		Set Form		
		Parameters: d: Director, y1: int, y2: int
		{ m: Movie | m.director == d &&
					 m.releaseYear >= y1 &&
					 m.releaseYear <= y2 }
	 */
		
		q.setClass(Movie.class);
		q.declareParameters("Director dt, int startYear, int endYear");
		
		q.setFilter("this.director == dt &&" +
					"this.releaseYear >= startYear &&" +
					"this.releaseYear <= endYear" );
		
		q.setOrdering("this.title ascending, this.releaseYear ascending");
		
		return (Collection<Movie>) q.execute(d, y1, y2);	
		
	}

	public static Collection<Object[]> allMovieStudioDirectorTuples(Query q) {

	/* Returns the collection of 3-tuples <m, s, d> such that movie m was made by studio s and
	   directed by director d. Sort the result by (m.releaseYear, m.title).
	   Use q.setResult(...). */

		q.setClass(Movie.class);
		
		q.setResult("this, this.studio.name, this.director.name");
		
		q.setOrdering("this.releaseYear ascending, this.title ascending");
				
		return (Collection<Object[]>) q.execute();

	}

	public static Collection<Object[]> groupMoviesByDirector(Query q) {

	/* Group movies by director and count the # of movies in each group.
	   Returns the collection of 2-tuples <d, n> such that director d directed n movies.
	   Sort the result by n in increasing order.
           Use q.setGrouping(...) and q.setResult(...).

	*/

		q.setClass(Movie.class);

		q.setGrouping("director.name");
		
		q.setResult("director.name, count(this.director.name)");
		
		q.setOrdering("count(this.director.name) ascending");
				
		return (Collection<Object[]>) q.execute();	
		
	}
}