package neige_i.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO generated from JSON with jsonschema2pojo.org
 * When a request is made to the Article Search API, its response is represented in a JSON format.
 * Next, Gson deserialize the JSON response to this POJO.
 */
@SuppressWarnings("unused")
public class ArticleSearch extends Topic {
    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public List<Article> getArticleList() {
        List<Article> articleList = new ArrayList<>();

        for (Doc doc : response.docs) {
            Article article = new Article();

            // Set the article's URL, title, section nd published date
            article.setUrl(doc.webUrl);
            article.setTitle(doc.headline.main);
            article.setSection(doc.newDesk + (doc.sectionName != null ? " > " + doc.sectionName : ""));
            article.setPublishedDate(doc.pubDate);

            // Set the article's thumbnail URL
            for (Multimedium multimedium : doc.multimedia)
                if (multimedium.width == 75)  // Size of a thumbnail
                    article.setThumbnailUrl("http://static01.nyt.com/" + multimedium.url);

            articleList.add(article);
        }
        return articleList;
    }

    // ------------------------------     AUTO-GENERATED CONTENT     -------------------------------

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    public class Response {

        @SerializedName("docs")
        @Expose
        private List<Doc> docs = null;
        @SerializedName("meta")
        @Expose
        private Meta meta;

        public List<Doc> getDocs() {
            return docs;
        }

        public void setDocs(List<Doc> docs) {
            this.docs = docs;
        }

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }
    }


    public class Meta {

        @SerializedName("hits")
        @Expose
        private Integer hits;
        @SerializedName("time")
        @Expose
        private Integer time;
        @SerializedName("offset")
        @Expose
        private Integer offset;

        public Integer getHits() {
            return hits;
        }

        public void setHits(Integer hits) {
            this.hits = hits;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }
    }


    public class Doc {

        @SerializedName("web_url")
        @Expose
        private String webUrl;
        @SerializedName("snippet")
        @Expose
        private String snippet;
        @SerializedName("lead_paragraph")
        @Expose
        private String leadParagraph;
        @SerializedName("abstract")
        @Expose
        private String _abstract;
        @SerializedName("print_page")
        @Expose
        private String printPage;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("headline")
        @Expose
        private Headline headline;
        @SerializedName("pub_date")
        @Expose
        private String pubDate;
        @SerializedName("document_type")
        @Expose
        private String documentType;
        @SerializedName("new_desk")
        @Expose
        private String newDesk;
        @SerializedName("section_name")
        @Expose
        private String sectionName;
        @SerializedName("subsection_name")
        @Expose
        private String subsectionName;
        @SerializedName("type_of_material")
        @Expose
        private String typeOfMaterial;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("word_count")
        @Expose
        private String wordCount;
        @SerializedName("slideshow_credits")
        @Expose
        private String slideshowCredits;
        @SerializedName("multimedia")
        @Expose
        private List<Multimedium> multimedia = null;

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getSnippet() {
            return snippet;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }

        public String getLeadParagraph() {
            return leadParagraph;
        }

        public void setLeadParagraph(String leadParagraph) {
            this.leadParagraph = leadParagraph;
        }

        public String getAbstract() {
            return _abstract;
        }

        public void setAbstract(String _abstract) {
            this._abstract = _abstract;
        }

        public String getPrintPage() {
            return printPage;
        }

        public void setPrintPage(String printPage) {
            this.printPage = printPage;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Headline getHeadline() {
            return headline;
        }

        public void setHeadline(Headline headline) {
            this.headline = headline;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getDocumentType() {
            return documentType;
        }

        public void setDocumentType(String documentType) {
            this.documentType = documentType;
        }

        public String getNewDesk() {
            return newDesk;
        }

        public void setNewDesk(String newDesk) {
            this.newDesk = newDesk;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getSubsectionName() {
            return subsectionName;
        }

        public void setSubsectionName(String subsectionName) {
            this.subsectionName = subsectionName;
        }

        public String getTypeOfMaterial() {
            return typeOfMaterial;
        }

        public void setTypeOfMaterial(String typeOfMaterial) {
            this.typeOfMaterial = typeOfMaterial;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWordCount() {
            return wordCount;
        }

        public void setWordCount(String wordCount) {
            this.wordCount = wordCount;
        }

        public String getSlideshowCredits() {
            return slideshowCredits;
        }

        public void setSlideshowCredits(String slideshowCredits) {
            this.slideshowCredits = slideshowCredits;
        }

        public List<Multimedium> getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(List<Multimedium> multimedia) {
            this.multimedia = multimedia;
        }
    }


    public class Headline {

        @SerializedName("main")
        @Expose
        private String main;
        @SerializedName("kicker")
        @Expose
        private String kicker;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getKicker() {
            return kicker;
        }

        public void setKicker(String kicker) {
            this.kicker = kicker;
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
}
