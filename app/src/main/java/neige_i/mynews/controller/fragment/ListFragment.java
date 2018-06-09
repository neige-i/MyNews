package neige_i.mynews.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neige_i.mynews.R;
import neige_i.mynews.model.Article;
import neige_i.mynews.view.DividerDecoration;
import neige_i.mynews.view.NewsAdapter;
import neige_i.mynews.view.TopicAdapter;

/**
 * This fragment displays the RecyclerView containing the article list.<br />
 * It is used in a ViewPager adapter.
 * @see TopicAdapter TopicAdapter
 */
@SuppressWarnings("WeakerAccess")
public class ListFragment extends Fragment {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * RecyclerView containing the list of the articles to display.
     */
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    // ----------------------------------     DATA VARIABLES     -----------------------------------

    /**
     * List of the articles to show, retrieved from the HTTP request response.
     */
    private final List<Article> mArticleList = new ArrayList<>();

    // ---------------------------------     STATIC VARIABLES     ----------------------------------

    /**
     * Key of the fragment index to display.
     */
    private static final String FRAGMENT_INDEX = "FRAGMENT_INDEX";

    // ----------------------------------     STATIC METHODS     -----------------------------------

    /**
     * This fragment displays different kind of news according to the specified position.
     * @param position  The position representing which kind of news to display.
     * @return  An instance of ListFragment class.
     */
    public static ListFragment newInstance(int position) {
        ListFragment listFragment = new ListFragment();

        // Set the bundle
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_INDEX, position);
        listFragment.setArguments(args);

        return listFragment;
    }

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, mainView);

        setFakeArticleList();
        configRecyclerView();

        return mainView;
    }

    // -----------------------------------     DATA METHODS     ------------------------------------

    /**
     * Populates the article list with fake ones. This is a temporary method.
     * Its purpose is only to have a list preview, before using HTTP request responses to update it.
     */
    private void setFakeArticleList() {
        for (int i = 0; i < 10; i++) {
            Article fakeArticle = new Article();
            fakeArticle.setTitle("This is the article title of the #" + (i+1) + " news");
            fakeArticle.setSection("Article's section");
            fakeArticle.setPublishedDate("2018-06-09");
            fakeArticle.setThumbnailUrl("https://pbs.twimg.com/profile_images/979726623657381889/ugnnQqXa_400x400.jpg");
            mArticleList.add(fakeArticle);
        }
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Configures the RecyclerView.
     */
    private void configRecyclerView() {
        mRecyclerView.setAdapter(new NewsAdapter(mArticleList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerDecoration(mRecyclerView.getContext()));
    }
}
