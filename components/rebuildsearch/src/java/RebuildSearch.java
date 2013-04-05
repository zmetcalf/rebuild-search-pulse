//Code snippets from 6.3.1 of Hibernate Search
//http://docs.jboss.org/hibernate/search/3.2/reference/en-US/html/manual-index-changes.html

import org.hibernate.search.Search;
import org.hibernate.search.FullTextSession;

public class RebuildSearch {


    FullTextSession fullTextSession = Search.getFullTextSession(session);

    fullTextSession.setFlushMode(FlushMode.MANUAL);
    fullTextSession.setCacheMode(CacheMode.IGNORE);
    transaction = fullTextSession.beginTransaction();
    //Scrollable results will avoid loading too many objects in memory
    
    ScrollableResults results = fullTextSession.createCriteria( Email.class )
        .setFetchSize(BATCH_SIZE)
        .scroll( ScrollMode.FORWARD_ONLY );
        
    int index = 0;
    
    while( results.next() ) {
        index++;
        fullTextSession.index( results.get(0) ); //index each element
        if (index % BATCH_SIZE == 0) {
            fullTextSession.flushToIndexes(); //apply changes to indexes
            fullTextSession.clear(); //free memory since the queue is processed
        }
    }
    transaction.commit();
}
