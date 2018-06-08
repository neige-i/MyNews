package neige_i.mynews.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArticleTest {

    @Test
    public void setPublishedDate() {
        // Initialize the String before formatting, and the expected one after formatting
        String stringBeforeFormat = "2018-06-25THAT PART DOES NOT MATTER",
                stringAfterFormat = "25/06/18";

        // Perform the String format
        Article article = new Article();
        article.setPublishedDate(stringBeforeFormat);

        // Check if the current String equals the expected one
        assertEquals(stringAfterFormat, article.getPublishedDate());
    }
}