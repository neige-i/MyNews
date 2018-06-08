package neige_i.mynews.util;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import neige_i.mynews.model.Topic;
import retrofit2.Response;

import static neige_i.mynews.util.NYTEndpoint.SECTION_BUSINESS;
import static neige_i.mynews.util.NYTEndpoint.SECTION_HOME;
import static org.junit.Assert.assertEquals;

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
     * It ensures that the HTTP request response gives a correct article list.
     * @param observable Launches the stream's subscription.
     */
    private void streamTest(Observable<? extends Response<? extends Topic>> observable) {
        // Create the Observer
        TestObserver<Response<? extends Topic>> testObserver = new TestObserver<>();

        // Launch the subscription
        observable.subscribeWith(testObserver).assertNoErrors().assertNoTimeout().awaitTerminalEvent();

        // Get the required topic from the Observer
        Topic topic = testObserver.values().get(0).body();

        // Get the beginning of the URL address of the first article of the list
        assert topic != null;
        String beginningUrl = topic.getArticleList().get(0).getUrl().substring(0, 24);

        // Check the validity of the URL
        assertEquals("https://www.nytimes.com/", beginningUrl);
    }
}