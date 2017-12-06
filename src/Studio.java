import java.util.*;
import javax.jdo.*;

@javax.jdo.annotations.PersistenceCapable

public class Studio
{
	String name; // key

	HashSet<Movie> movies = new HashSet<Movie>();
	             // The set of movies made by this studio


	public String toString()
	{
		return name;
	}

	public static Studio find(String sName, PersistenceManager pm) {

	/* Returns the studio with the given name "sName";
           if no such studio exists, null is returned. 
           The function is applied to the database held by the persistence manager "pm". 

		Set Form		
		Parameters: sName: String
		{ s: Studio | s.name == sName }

	 */
	 
		Query q = pm.newQuery(Studio.class);
		q.declareParameters("String studioName");
		q.setFilter("this.name == studioName");
		
		Collection<Studio> sn = (Collection<Studio>) q.execute(sName);
		
		Studio s = Utility.extract(sn);	
		q.close(sn);
		
		return s;

	}

	public static Collection<Studio> studiosWithThisActor(String aName, Query q) {

	/* Returns the collection of all studios that have made at least one movie
	   in which an actor with the name "aName" acted.
	   Sort the result by Studio.name.

		Set Form		
		Parameters: aName: String
		Bound variable: m: Movie
		Unbound variable a: Actor
		{ s: Studio | s.movies.contains(m) &&
					  m.actors.contains(a) &&
					  a.name == aName }

	*/
	
		q.setClass(Studio.class);
		q.declareParameters("String actorName");
		q.declareVariables("Movie m");
		
		q.setFilter("this.movies.contains(m) &&" +
					"m.actors.contains(a) &&" +
					"a.name == actorName" );
		
		q.setOrdering("this.name ascending");
		
		return (Collection<Studio>) q.execute(aName);
		
	}

	public static Collection<Studio> studiosMinimumActors(int x, Query q) {

	/* Returns the collection of all studios that have made a movie in which at least "x" actors acted.
	   Sort the result by Studio.name.

		Set Form		
		Parameters: x: int
		Bound variable: m: Movie
		{ s: Studio | s.movies.contains(m) &&
					  m.actors.size() >= x }
		
	*/
		
		q.setClass(Studio.class);
		q.declareParameters("int numberOfActors");
		q.declareVariables("Movie m");
		
		q.setFilter("this.movies.contains(m) &&" +
					"m.actors.size() >= numberOfActors" );
		
		q.setOrdering("this.name ascending");
		
		return (Collection<Studio>) q.execute(x);

	}

	public static Collection<Studio> studiosInThisYear(int yr, Query q) {

	/* Returns the collection of all studios "s" that satisfy the following condition:

	   "s" has made at least one movie released in year "yr" that had
	   an actor that acted in another movie which was released in the same year "yr" and
	   was NOT made by the studio "s".

	   Sort the result by Studio.name.

		Set Form		
		Parameters: yr: int
		Bound variable: m1: Movie, m2: Movie
		Unbound variable: a: Actor
		{ s: Studio | s.movies.contains(m1) &&
					  m1.releaseYear == yr &&
					  m1.actors.contains(a) &&
					  a.movies.contains(m2) &&
					  m2.releaseYear == yr &&
					  m1.studio.name != m2.studio.name }
		
	*/
		
		q.setClass(Studio.class);
		q.declareParameters("int movieYear");
		q.declareVariables("Movie m1, m2");
		
		q.setFilter("this.movies.contains(m1) &&" +
					"m1.releaseYear == movieYear &&" +
					"m1.actors.contains(a) &&" +
					"a.movies.contains(m2) &&" +
					"m2.releaseYear == movieYear &&" +
					"m1.studio.name != m2.studio.name" );
		
		q.setOrdering("this.name ascending");
		
		return (Collection<Studio>) q.execute(yr);

	}
}