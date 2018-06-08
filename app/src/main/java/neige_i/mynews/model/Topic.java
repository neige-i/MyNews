package neige_i.mynews.model;

import java.util.List;

/**
 * Super class that represents a topic to get the news from.
 * @see TopStories
 * @see MostPopular
 * @see ArticleSearch
 */
public abstract class Topic {

    /**
     * Retrieves a list of Article objects, that are more convenient to use than the generated POJOs
     * from JSON request responses.
     * @return The list of articles contained in the request response.
     */
    public abstract List<Article> getArticleList();
}
