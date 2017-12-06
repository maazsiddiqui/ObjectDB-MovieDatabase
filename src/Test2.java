import java.util.*;
import javax.jdo.*;
import com.objectdb.Utilities;

public class Test2
{
	public static void main(String argv[])
	{
		PersistenceManager pm = Utilities.getPersistenceManager( "movie2.odb" );

		System.out.println( "-- TEST Studio.find --\n" );
		Studio queens = Studio.find( "Queens Indie Film", pm );
		Studio cent = Studio.find( "21st Century Film", pm );

		System.out.println( queens );
		System.out.println( cent );
		System.out.println();

		System.out.println( "-- TEST Studio.studiosWithThisActor --\n" );
		Query q = pm.newQuery();
		Collection<Studio> ss = Studio.studiosWithThisActor("Cathleen Beckham", q);
		Utility.printCollection( ss );
		q.closeAll();
		System.out.println();

		System.out.println( "-- TEST Studio.studiosMinimumActors --\n" );
		q = pm.newQuery();
		ss = Studio.studiosMinimumActors(2, q);
		Utility.printCollection( ss );
		q.closeAll();
		System.out.println();

		System.out.println( "-- TEST Studio.studiosInThisYear --\n" );
		q = pm.newQuery();
		ss = Studio.studiosInThisYear(2000, q);
		Utility.printCollection( ss );
		q.closeAll();
		System.out.println();

		System.out.println( "-- TEST Actor.actedBetweenTheseYears --\n" );
		q = pm.newQuery();
		Collection<Actor> aa = Actor.actedBetweenTheseYears(2005, 2008, q);
		Utility.printCollection( aa );
		q.closeAll();
		System.out.println();

		System.out.println( "-- TEST Actor.actedForThisStudio --\n" );
		q = pm.newQuery();
		aa = Actor.actedForThisStudio("Cinema Universals", q);
		Utility.printCollection( aa );
		q.closeAll();
		System.out.println();

		System.out.println( "-- TEST Director.find and Director.movies --\n" );
		Director d = Director.find("John Ford", pm);
		q = pm.newQuery();
		Collection<Movie> mm = d.movies(q);
		Utility.printCollection( mm );
		q.closeAll();
		System.out.println();

		System.out.println( "-- TEST Director.studiosWithThisDirector --\n" );
		q = pm.newQuery();
		ss = d.studiosWithThisDirector(q);
		Utility.printCollection( ss );
		q.closeAll();
		System.out.println();

		System.out.println( "-- TEST Movie.sameTitle --\n" );
		q = pm.newQuery();
		mm = Movie.sameTitle(q);
		Utility.printCollection( mm );
		q.closeAll();
		System.out.println();

		System.out.println( "-- TEST Movie.studioActors --\n" );
		q = pm.newQuery();
		mm = Movie.studioActors("Queens Indie Film", 2, q);
		Utility.printCollection( mm );
		q.closeAll();
		System.out.println();

		System.out.println( "-- TEST Movie.madeByDirectorBetweenTheseYears --\n" );
		q = pm.newQuery();
		mm = Movie.madeByDirectorBetweenTheseYears(d, 1970, 2005, q);
		Utility.printCollection( mm );
		q.closeAll();
		System.out.println();

		System.out.println( "-- TEST Movie.allMovieStudioDirectorTuples --\n" );
		q = pm.newQuery();
		Collection<Object[]> result = Movie.allMovieStudioDirectorTuples(q);
		Utility.printCollectionOfArrays( result );
		q.closeAll();

		System.out.println( "-- TEST Movie.groupMoviesByDirector --\n" );
		q = pm.newQuery();
		result = Movie.groupMoviesByDirector(q);
		Utility.printCollectionOfArrays( result );
		q.closeAll();

		if ( !pm.isClosed() )
        		pm.close();
	}
}