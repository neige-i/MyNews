package neige_i.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO generated from JSON with jsonschema2pojo.org
 * When a request is made to the Top Stories API, its response is represented in a JSON format.
 * Next, Gson deserialize the JSON response to this POJO.
 */
@SuppressWarnings("unused")
public class TopStories extends Topic {
    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public List<Article> getArticleList() {
        List<Article> articleList = new ArrayList<>();

        for (Result result : results) {
            Article article = new Article();

            // Set the article's URL, title, section nd published date
            article.setUrl(result.url);
            article.setTitle(result.title);
            article.setSection(result.section + (!result.subsection.isEmpty() ? " > " + result.subsection : ""));
            article.setPublishedDate(result.publishedDate);

            // Set the article's thumbnail URL
            for (Multimedium multimedium : result.multimedia)
                if (multimedium.width == 75) // Size of a thumbnail
                    article.setThumbnailUrl(multimedium.url);

            articleList.add(article);
        }
        return articleList;
    }

    // ------------------------------     AUTO-GENERATED CONTENT     -------------------------------

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public class Result {

        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("subsection")
        @Expose
        private String subsection;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("abstract")
        @Expose
        private String _abstract;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("thumbnail_standard")
        @Expose
        private String thumbnailStandard;
        @SerializedName("short_url")
        @Expose
        private String shortUrl;
        @SerializedName("byline")
        @Expose
        private String byline;
        @SerializedName("item_type")
        @Expose
        private String itemType;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("material_type_facet")
        @Expose
        private String materialTypeFacet;
        @SerializedName("kicker")
        @Expose
        private String kicker;
        @SerializedName("multimedia")
        @Expose
        private List<Multimedium> multimedia = null;
        @SerializedName("related_urls")
        @Expose
        private List<RelatedUrl> relatedUrls = null;

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSubsection() {
            return subsection;
        }

        public void setSubsection(String subsection) {
            this.subsection = subsection;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAbstract() {
            return _abstract;
        }

        public void setAbstract(String _abstract) {
            this._abstract = _abstract;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnailStandard() {
            return thumbnailStandard;
        }

        public void setThumbnailStandard(String thumbnailStandard) {
            this.thumbnailStandard = thumbnailStandard;
        }

        public String getShortUrl() {
            return shortUrl;
        }

        public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
        }

        public String getByline() {
            return byline;
        }

        public void setByline(String byline) {
            this.byline = byline;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getMaterialTypeFacet() {
            return materialTypeFacet;
        }

        public void setMaterialTypeFacet(String materialTypeFacet) {
            this.materialTypeFacet = materialTypeFacet;
        }

        public String getKicker() {
            return kicker;
        }

        public void setKicker(String kicker) {
            this.kicker = kicker;
        }

        public List<Multimedium> getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(List<Multimedium> multimedia) {
            this.multimedia = multimedia;
        }

        public List<RelatedUrl> getRelatedUrls() {
            return relatedUrls;
        }

        public void setRelatedUrls(List<RelatedUrl> relatedUrls) {
            this.relatedUrls = relatedUrls;
        }
    }


    public class Multimedium {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("format")
        @Expose
        private String format;
        @SerializedName("height")
        @Expose
        private Integer height;
        @SerializedName("width")
        @Expose
        private Integer width;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("subtype")
        @Expose
        private String subtype;
        @SerializedName("caption")
        @Expose
        private String caption;
        @SerializedName("copyright")
        @Expose
        private String copyright;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }
    }


    public class RelatedUrl {

        @SerializedName("suggested_link_text")
        @Expose
        private String suggestedLinkText;
        @SerializedName("url")
        @Expose
        private String url;

        public String getSuggestedLinkText() {
            return suggestedLinkText;
        }

        public void setSuggestedLinkText(String suggestedLinkText) {
            this.suggestedLinkText = suggestedLinkText;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
