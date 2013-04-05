import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torweg.pulse.annotations.Action;
import org.torweg.pulse.annotations.AnyAction;

public class RebuildSearchController extends Controller {
    
    private static final Logger Logger = LoggerFactory
        .getLogger(RebuildSearchController.class);
        
    @Action("rebuildSearch")
    public final void rebuildHibernateSearch() {
    
    }
    @AnyAction
    public final void alwaysRun() {
        LOGGER.info("success of rebuild");
    }

}
