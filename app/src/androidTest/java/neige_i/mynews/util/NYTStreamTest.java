package neige_i.mynews.util;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static neige_i.mynews.util.NYTEndpoint.SECTION_BUSINESS;
import static neige_i.mynews.util.NYTEndpoint.SECTION_HOME;
import static org.junit.Assert.assertTrue;

public class NYTStreamTest {
    // ------------------------------------     TEST METHODS     -----------------------------------

    /**
     * Tests the stream with the Top Stories API.
     */
    @Test
    public void streamTopStories() {
        streamTest(NYTStream.streamTopStories(SECTION_HOME));
        streamTest(NYTStream.streamTopStories(SECTION_BUSINESS));
    }

    /**
     * Tests the stream with the Most Popular API.
     */
    @Test
    public void streamMostPopular() {
        streamTest(NYTStream.streamMostPopular());
    }

    /**
     * Tests the stream with the Article Search API.
     */
    @Test
    public void streamArticleSearch() throws InterruptedException {
        // Must sleep a while to avoid getting the HTTP status code: 429 (Too many requests)
        // 1 second is the request rate limit
        streamTest(NYTStream.streamArticleSearch("baseball", null, null, null));
        Thread.sleep(1000);
        streamTest(NYTStream.streamArticleSearch(null, "19840424", null, null));
        Thread.sleep(1000);
        streamTest(NYTStream.streamArticleSearch(null, null, "20150219", null));
        Thread.sleep(1000);
        streamTest(NYTStream.streamArticleSearch(null, null, null, "news_desk:(\"arts\")"));
    }

    // ----------------------------------     PRIVATE METHODS     ----------------------------------

    /**
     * Launches a stream with the specified observable to execute the HTTP request.
     * It ensures that the HTTP request response is successful.
     * @param observable Launches the stream's subscription.
     */
    private void streamTest(Observable<Response<ResponseBody>> observable) {
        // Create the Observer
        TestObserver<Response<ResponseBody>> testObserver = new TestObserver<>();

        // Launch the subscription
        observable.subscribeWith(testObserver).assertNoErrors().assertNoTimeout().awaitTerminalEvent();

       // Check if the request response is successful
       assertTrue(testObserver.values().get(0).isSuccessful());
    }
}