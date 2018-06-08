package neige_i.mynews.util;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import neige_i.mynews.model.Topic;
import retrofit2.Response;

import static neige_i.mynews.util.NYTEndpoint.SECTION_BUSINESS;
import static neige_i.mynews.util.NYTEndpoint.SECTION_HOME;
import static org.junit.Assert.assertTrue;

public class NYTEndpointTest {
    // --------------------------------     INSTANCE VARIABLES     ---------------------------------

    /**
     * Endpoint that is used for all HTTP request.
     */
    private NYTEndpoint mEndpoint;

    // ------------------------------------     SETUP METHOD     -----------------------------------

    @Before
    public void createEndpoint() {
        mEndpoint = NYTEndpoint.retrofit.create(NYTEndpoint.class);
    }

    // ------------------------------------     TEST METHODS     -----------------------------------

    /**
     * Tests the Top Stories API.
     */
    @Test
    public void getTopStories() {
        testRequest(mEndpoint.getTopStories(SECTION_HOME));
        testRequest(mEndpoint.getTopStories(SECTION_BUSINESS));
    }

    /**
     * Tests the Most Popular API.
     */
    @Test
    public void getMostPopularArticles() {
        testRequest(mEndpoint.getMostPopularArticles());
    }

    /**
     * Tests the Article Search API.
     */
    @Test
    public void getSearchedArticles() throws InterruptedException {
        // Must sleep a while to avoid getting the HTTP status code: 429 (Too many requests)
        // 1 second is the request rate limit
        testRequest(mEndpoint.getSearchedArticles("basketball", null, null, null));
        Thread.sleep(1000);
        testRequest(mEndpoint.getSearchedArticles(null, "19940602", null, null));
        Thread.sleep(1000);
        testRequest(mEndpoint.getSearchedArticles(null, null, "20130604", null));
        Thread.sleep(1000);
        testRequest(mEndpoint.getSearchedArticles(null, null, null, "news_desk:(\"sports\")"));
    }

    // ----------------------------------     PRIVATE METHODS     ----------------------------------

    /**
     * Launches a stream with the specified observable to execute the HTTP request.
     * It ensures that the HTTP request response is successful.
     * @param observable Launches the stream's subscription.
     */
    private void testRequest(Observable<? extends Response<? extends Topic>> observable) {
        // Create the Observer
        TestObserver<Response> testObserver = new TestObserver<>();

        // Launch the subscription
        observable.subscribeWith(testObserver).assertNoErrors().assertNoTimeout().awaitTerminalEvent();

        // Check if the request response is successful
        assertTrue(testObserver.values().get(0).isSuccessful());
    }
}