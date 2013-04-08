package org.torweg.pulse.component.rebuildsearch;

//Code snippets from 6.3.1 of Hibernate Search
//http://docs.jboss.org/hibernate/search/3.2/reference/en-US/html/manual-index-changes.html

import org.hibernate.annotations.BatchSize;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.ScrollableResults;
import org.hibernate.search.Search;
import org.hibernate.search.FullTextSession;
import org.hibernate.ScrollMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.torweg.pulse.component.cms.model.CMSContent;
import org.torweg.pulse.invocation.lifecycle.Lifecycle;

public class RebuildSearch {

    private static final int BATCH_SIZE = 50; 

    public RebuildSearch() {
    }

    public void rebuild() {
        Session session = Lifecycle.getHibernateDataSource().createNewSession();
        
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.setFlushMode(FlushMode.MANUAL);
        fullTextSession.setCacheMode(CacheMode.IGNORE);
        
        Transaction transaction = fullTextSession.beginTransaction();
        //Scrollable results will avoid loading too many objects in memory
        
        ScrollableResults results = fullTextSession.createCriteria( CMSContent.class )
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
}
