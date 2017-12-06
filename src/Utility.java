import java.util.*;

public class Utility
{
	public static <T> void printCollection(Collection<T> c)
	{
		for (T x: c)
			System.out.println( x );

		/*The above is equivalent to:

		  Iterator<T> it = c.iterator();

		  while ( it.hasNext() )
		    	System.out.println( it.next() );
		*/
	}

	public static void printCollectionOfArrays(Collection<Object[]> arrays)
	{
		for ( Object[] x : arrays )
		{
			for ( int i =0; i < x.length; i++ )
				System.out.println( x[i] );
			System.out.println();
		}
	}

	public static <T> T extract(Collection<T> c)

	/* Returns the last element of collection c, "last" according to the order of
	   the iteration of the for-loop. In particular, if c contains only one element,
	   that element is returned. If c is empty, null is returned. */

	{
		T x = null;
		for (T e: c)
			x = e;
		return x;
	}
}