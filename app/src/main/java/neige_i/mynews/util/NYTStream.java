package neige_i.mynews.util;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import neige_i.mynews.model.Topic;
import retrofit2.Response;

/**
 * Class that executes the HTTP requests through streams to implement reactive programming.
 */
public class NYTStream {
    // ---------------------------------     INSTANCE VARIABLES     --------------------------------

    /**
     * Constant to execute the HTTP request to the Top Stories API.
     * @see #streamRequest(int, String, String, String, String, String)} streamRequest()}.
     */
    private static final int API_TOP_STORIES = 0;

    /**
     * Constant to execute the HTTP request to the Most Popular API.
     * @see #streamRequest(int, String, String, String, String, String)} streamRequest()}.
     */
    private static final int API_MOST_POPULAR = 1;

    /**
     * Constant to execute the HTTP request to the Article Search API.
     * @see #streamRequest(int, String, String, String, String, String)} streamRequest()}.
     */
    private static final int API_ARTICLE_SEARCH = 2;

    // ----------------------------------     STREAM METHODS     -----------------------------------

    /**
     * Returns the Observable that emits the subscription to the stream so as to execute the request
     * to the Top Stories API.
     * @param section The section of the articles to get.
     * @return The Observable of the request stream.
     */
    public static Observable<? extends Response<? extends Topic>> streamTopStories(String section) {
        return streamRequest(API_TOP_STORIES, section, null, null, null, null);
    }

    /**
     * Returns the Observable that emits the subscription to the stream so as to execute the request
     * to the Most Popular API.
     * @return The Observable of the request stream.
     */
    public static Observable<? extends Response<? extends Topic>> streamMostPopular() {
        return streamRequest(API_MOST_POPULAR, null, null, null, null, null);
    }

    /**
     * Returns the Observable that emits the subscription to the stream so as to execute the request
     * to the Article Search API.
     * @param query       The query term(s) to search.
     * @param beginDate   Publication date from which the articles are searched.
     * @param endDate     Publication date to which the articles are searched.
     * @param filterQuery Filtered search to indicate the categories of the articles to search.
     * @return The Observable of the request stream.
     */
    public static Observable<? extends Response<? extends Topic>> streamArticleSearch(String query, String beginDate, String endDate, String filterQuery) {
        return streamRequest(API_ARTICLE_SEARCH, null, query, beginDate, endDate, filterQuery);
    }

    // ----------------------------------     PRIVATE METHODS     ----------------------------------

    /**
     * Returns the Observable that emits the subscription to the stream so as to execute the request
     * to the desired API (specified by its id).
     * @param apiId         The id of the topic to access.
     * @param section       The section of the articles to get, for Top Stories API.
     * @param query         The search query term(s), for Article Search API.
     * @param beginDate     Publication date from which the articles are searched, for Article Search API.
     * @param endDate       Publication date to which the articles are searched, for Article Search API.
     * @param filterQuery   Filtered search to indicate the categories of the articles to search, for Article Search API.
     * @return              The Observable of the request stream.
     */
    private static Observable<? extends Response<? extends Topic>> streamRequest(int apiId, String section, String query, String beginDate, String endDate, String filterQuery) {
        // Create the Endpoint
        NYTEndpoint nytEndpoint = NYTEndpoint.retrofit.create(NYTEndpoint.class);

        // Get the Observable
        Observable<? extends Response<? extends Topic>> topicObservable = null;
        switch (apiId) {
            case API_TOP_STORIES:       topicObservable = nytEndpoint.getTopStories(section);   break;
            case API_MOST_POPULAR:      topicObservable = nytEndpoint.getMostPopularArticles(); break;
            case API_ARTICLE_SEARCH:    topicObservable = nytEndpoint.getSearchedArticles(query, beginDate, endDate, filterQuery);  break;
        }

        // Configure the stream and return the Observable
        assert topicObservable != null;
        return topicObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5, TimeUnit.SECONDS);
    }
}
