package neige_i.mynews.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import neige_i.mynews.R;
import neige_i.mynews.model.Article;

/**
 * This RecyclerView adapter allows user to scroll the article list.<br />
 * The content of each item of the list is defined in a ViewHolder.
 * @see NewsHolder
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
    // --------------------------------     CALLBACK INTERFACES     --------------------------------

    /**
     * Callback interface to spread the click event from the ViewHolder to the fragment.
     */
    public interface OnArticleClickListener {
        /**
         * Called when an article has been clicked in the RecyclerView.
         * @param clickedArticle The clicked article.
         */
        void onArticleClick(Article clickedArticle);
    }

    // ----------------------------------     DATA VARIABLES     -----------------------------------

    /**
     * List of the articles to display in the RecyclerView.
     */
    private final List<Article> mArticleList;

    /**
     * Callback handling click events.
     */
    private final OnArticleClickListener mCallback;

    /**
     * List of the titles of the already read articles.
     */
    private List<String> mReadArticles;

    // -----------------------------------     CONSTRUCTORS     ------------------------------------

    public NewsAdapter(List<Article> articleList, OnArticleClickListener callback) {
        mArticleList = articleList;
        mCallback = callback;
    }

    // ---------------------------------     GETTERS & SETTERS     ---------------------------------

    public void setReadArticles(List<String> readArticles) {
        mReadArticles = readArticles;
        notifyDataSetChanged();
    }

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        holder.updateUI(mArticleList.get(position), mCallback, mReadArticles);
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}
