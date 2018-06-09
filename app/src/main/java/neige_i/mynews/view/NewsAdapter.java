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
    // ----------------------------------     DATA VARIABLES     -----------------------------------

    /**
     * List of the articles to display in the RecyclerView.
     */
    private final List<Article> mArticleList;

    // -----------------------------------     CONSTRUCTORS     ------------------------------------

    public NewsAdapter(List<Article> articleList) {
        mArticleList = articleList;
    }

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        holder.updateUI(mArticleList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}
