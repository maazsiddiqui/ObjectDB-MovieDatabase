import java.util.*;
import javax.jdo.*;

@javax.jdo.annotations.PersistenceCapable

public class Director extends Person {

	public static Director find(String name, PersistenceManager pm) {

	/* 	Returns a director with the given "name";
        if no such director exists, null is returned. 
        The function is applied to the database held by the persistence manager "pm".

		Set Form		
		Parameters: name: String
		{ d: Director | d.name == name }

	 */
	  
		Query q = pm.newQuery(Director.class);
		q.declareParameters("String directorName");
		q.setFilter("this.name == directorName");
		
		Collection<Director> dn = (Collection<Director>) q.execute(name);
		
		Director d = Utility.extract(dn);	
		q.close(dn);
		
		return d;

	}

	public Collection<Movie> movies(Query q) {

	/* 	Returns the collection of all movies directed by this director. 
	   	Represents the inverse of Movie.director.
	   	Sort the result by (Movie.title, Movie.releaseYear).
	   
		Set Form		
		Parameters: d: Director		
		{ m: Movie | this.director.name == d.name }
	   
	  */

		q.setClass(Movie.class);
		q.declareParameters("Director d");
		
		q.setFilter("this.director.name == d.name" );
		
		q.setOrdering("this.title ascending, this.releaseYear ascending");
		
		return (Collection<Movie>) q.execute(this);

	}

	public Collection<Studio> studiosWithThisDirector(Query q) {

	/* 	Returns the collection of all studios that have made at least two movies
	   	directed by this director.
	   	Sort the result by Studio.name. 
	   
	   	Set Form		
		Parameters: d: Director		
		{ s: Studio | (this.movies.director.name == d.name).size() >= 2 }

	 */

		q.setClass(Studio.class);
		q.declareParameters("Director d");
		q.setFilter("(this.movies.director.name == d.name).size() >= 2");
		
		q.setOrdering("this.name ascending");
		
		return (Collection<Studio>) q.execute(this);	

	}
}