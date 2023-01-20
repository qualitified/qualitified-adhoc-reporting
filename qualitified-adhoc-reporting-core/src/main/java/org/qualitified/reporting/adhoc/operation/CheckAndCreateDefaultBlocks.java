package org.qualitified.reporting.adhoc.operation;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.NuxeoException;

@Operation(id = CheckAndCreateDefaultBlocks.ID, category = Constants.CAT_DOCUMENT, label = "Check and Create Default Blocks", description = "Checks if a query request returns any results, and if not, creates the default blocks for the Nuxeo dashboard.")
public class CheckAndCreateDefaultBlocks {

    public static final String ID = "Document.CheckAndCreateDefaultBlocks";

    @Context
    protected OperationContext ctx;

    @OperationMethod
    public void run() throws NuxeoException {
        // Define the query string
        String query = "SELECT * FROM Document WHERE ecm:primaryType in ('ChartBar', 'ChartLine', 'ChartPie', 'Count', 'Table', 'List') AND ecm:isTrashed = 0  AND ecm:currentLifeCycleState != 'deleted' ORDER BY dc:created ASC\n";

        // Execute the query
        DocumentModelList results = ctx.getCoreSession().query(query);
        // Check if the query returned any results
        if (results.isEmpty()) {
            // If the query did not return any results, create the default blocks
            createDefaultBlocks();
        }
    }

    protected void createDefaultBlocks() {
        // Code to create the default blocks goes here
        // ...
        CoreSession session = ctx.getCoreSession();


        // Create the Recently Modified block
        DocumentModel modifiedDoc = session.createDocumentModel("/", "Modified Recently", "List");

        modifiedDoc.setPropertyValue("dc:title", "Modified Recently");
        modifiedDoc.setPropertyValue("list:listSelected", "modified");
        modifiedDoc.setPropertyValue("report:width", 7);
        modifiedDoc.setPropertyValue("report:height", 3);
        modifiedDoc.setPropertyValue("report:row", 0);
        modifiedDoc.setPropertyValue("report:col", 0);
        session.createDocument(modifiedDoc);

        // Create the Favorites block
        DocumentModel favoritesDoc = session.createDocumentModel("/", "Favorites", "List");

        favoritesDoc.setPropertyValue("dc:title", "Favorites");
        favoritesDoc.setPropertyValue("list:listSelected", "favorites");
        favoritesDoc.setPropertyValue("report:width", 3);
        favoritesDoc.setPropertyValue("report:height", 3);
        favoritesDoc.setPropertyValue("report:row", 0);
        favoritesDoc.setPropertyValue("report:col", 7);
        session.createDocument(favoritesDoc);

        // Create the Tasks block
        DocumentModel tasksDoc = session.createDocumentModel("/", "Tasks", "List");

        tasksDoc.setPropertyValue("dc:title", "Tasks");
        tasksDoc.setPropertyValue("list:listSelected", "tasks");
        tasksDoc.setPropertyValue("report:width", 3);
        tasksDoc.setPropertyValue("report:height", 3);
        tasksDoc.setPropertyValue("report:row", 3);
        tasksDoc.setPropertyValue("report:col", 0);
        session.createDocument(tasksDoc);

        // Create the Recently Viewed block
        DocumentModel viewedDoc = session.createDocumentModel("/", "Recently Viewed", "List");

        viewedDoc.setPropertyValue("dc:title", "Recently Viewed");
        viewedDoc.setPropertyValue("list:listSelected", "recentlyViewed");
        viewedDoc.setPropertyValue("report:width", 3);
        viewedDoc.setPropertyValue("report:height", 3);
        viewedDoc.setPropertyValue("report:row", 3);
        viewedDoc.setPropertyValue("report:col", 7);
        session.createDocument(viewedDoc);

    }
}
