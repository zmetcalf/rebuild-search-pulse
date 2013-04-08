package org.torweg.pulse.component.rebuildsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.torweg.pulse.annotations.Action;
import org.torweg.pulse.annotations.AnyAction;
import org.torweg.pulse.bundle.Controller;

public class RebuildSearchController extends Controller {
    
    private static final Logger LOGGER = LoggerFactory
        .getLogger(RebuildSearchController.class);
        
    @Action("rebuildSearch")
    public final void rebuildHibernateSearch() {
    
        RebuildSearch rebuilder = new RebuildSearch();
        rebuilder.rebuild();
    
    }
    @AnyAction
    public final void alwaysRun() {
        LOGGER.info("success of rebuild");
    }
}
