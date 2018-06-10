package neige_i.mynews.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import neige_i.mynews.R;
import neige_i.mynews.model.Article;

/**
 * This RecyclerView ViewHolder defines the content of each item of the RecyclerView.<br />
 * It is used inside a RecyclerView adapter.
 * @see NewsAdapter
 */
@SuppressWarnings("WeakerAccess")
public class NewsHolder extends RecyclerView.ViewHolder {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * ImageView that represents the article's thumbnail.
     */
    @BindView(R.id.thumbnail) ImageView mThumbnail;

    /**
     * TextView that represents the article's section.
     */
    @BindView(R.id.section) TextView mSection;

    /**
     * TextView that represents the article's published date.
     */
    @BindView(R.id.publishedDate) TextView mPublishedDate;

    /**
     * TextView that represents the article's title.
     */
    @BindView(R.id.newsTitle) TextView mTitle;

    // -----------------------------------     CONSTRUCTORS     ------------------------------------

    public NewsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Updates the layout of the RecyclerView item with the information provided by the specified article.
     * @param article   Article that contains the information to update the layout.
     * @param callback  Callback to spread the click event to the fragment.
     */
    public void updateUI(final Article article, final NewsAdapter.OnArticleClickListener callback) {
        // Update the UI
        Glide.with(itemView).load(article.getThumbnailUrl())
                .apply(new RequestOptions().transform(new RoundedCorners(10)))
                .into(mThumbnail);
        mSection.setText(article.getSection());
        mPublishedDate.setText(article.getPublishedDate());
        mTitle.setText(article.getTitle());

        // Spread the click event
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onArticleClick(article);
            }
        });
    }
}
