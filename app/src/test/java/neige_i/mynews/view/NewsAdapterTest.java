package neige_i.mynews.view;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import neige_i.mynews.model.Article;

import static org.junit.Assert.assertEquals;

public class NewsAdapterTest {

    @Test
    public void getItemCount() {
        // Initialize a list containing 5 articles
        List<Article> articleList = new ArrayList<>(Arrays.asList(
                new Article(), new Article(), new Article(), new Article(), new Article()
        ));

        // Check if the actual item count equals the number of articles (here: 5)
        NewsAdapter newsAdapter = new NewsAdapter(articleList, null);
        assertEquals(5, newsAdapter.getItemCount());
    }
}