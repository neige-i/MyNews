package neige_i.mynews.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class represents an article from one of the available topic.
 * @see Topic
 */
public class Article {
    // --------------------------------     INSTANCE VARIABLES     ---------------------------------

    /**
     * The article's URL address.
     */
    private String mUrl;

    /**
     * The article's title.
     */
    private String mTitle;

    /**
     * The article's section.
     */
    private String mSection;

    /**
     * The article's date of publication.
     */
    private String mPublishedDate;

    /**
     * The article's thumbnail URL.
     */
    private String mThumbnailUrl;

    // --------------------------------     GETTERS AND SETTERS     --------------------------------

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    /**
     * Changes the date format.
     * @param publishedDate The String that contains the published date.
     */
    public void setPublishedDate(String publishedDate) {
        // At the beginning, the date is in the following format: "yyyy-MM-dd....."

        publishedDate = publishedDate.substring(0, 10); // Truncate the String to the 10 first characters

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(publishedDate); // String -> Date
            mPublishedDate = new SimpleDateFormat("dd/MM/yy", Locale.US).format(date); // Date -> String
        } catch (ParseException ignored) {}

        // At the end, the date is in the following format: "dd/MM/yy"
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }
}
