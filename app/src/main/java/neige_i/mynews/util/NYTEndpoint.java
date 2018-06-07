package neige_i.mynews.util;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface that contains the HTTP requests to the New York Times APIs.
 * The requests are performed with retrofit.
 */
public interface NYTEndpoint {
    // ---------------------------------     INSTANCE VARIABLES     --------------------------------

    /**
     * Key to access the New York Times APIs.
     */
    String API_KEY = "51ddebd9930a432a86cbc0f62c93d21f";

    // ----------------------------------     PATH VARIABLES     -----------------------------------

    /**
     * Path that represents the section to look for when executing the request to get the top stories.
     * With this variable, the retrieved articles belong to the 'home' section.
     * @see #getTopStories(String)
     */
    String SECTION_HOME = "home";

    /**
     * Path that represents the section to look for when executing the request to get the top stories.
     * With this variable, the retrieved articles belong to the 'business' section.
     * @see #getTopStories(String)
     */
    String SECTION_BUSINESS = "business";

    // -------------------------------     HTTP REQUEST VARIABLES     ------------------------------

    /**
     * Base variable that is used for all HTTP requests.
     */
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.nytimes.com/svc/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    // --------------------------------     HTTP REQUEST METHODS     -------------------------------

    /**
     * Executes a request to the Top Stories API.
     * The request gets the top stories that belong to the specified section.
     * @param section The section of the articles to get. Must be either {@link #SECTION_HOME} or
     *                {@link #SECTION_BUSINESS}.
     * @return The response to the API request, inside an Observable collection to use reactive programming.
     */
    @GET("topstories/v2/{section}.json?api-key=" + API_KEY)
    Observable<Response<ResponseBody>> getTopStories(@Path("section") String section);

    /**
     * Executes a request to the Most Popular API.
     * The request gets the most viewed articles in all sections for the last week.
     * @return The response to the API request, inside an Observable collection to use reactive programming.
     */
    @GET("mostpopular/v2/mostviewed/all-sections/7.json?api-key=" + API_KEY)
    Observable<Response<ResponseBody>> getMostPopularArticles();

    /**
     * Executes a request to the Article Search API.
     * The request gets the articles, sort by the newest, that match the specified search parameters.
     * @param query       The search query term(s).
     * @param beginDate   Publication date from which the articles are searched.
     * @param endDate     Publication date to which the articles are searched.
     * @param filterQuery Filtered search parameter to indicate the categories of the articles to search.
     * @return The response to the API request, inside an Observable collection to use reactive programming.
     */
    @GET("search/v2/articlesearch.json?sort=newest&api-key=" + API_KEY)
    Observable<Response<ResponseBody>> getSearchedArticles(@Query("q") String query,
                                             @Query("begin_date") String beginDate,
                                             @Query("end_date") String endDate,
                                             @Query("fq") String filterQuery);
}
