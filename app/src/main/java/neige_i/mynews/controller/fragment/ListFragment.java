package neige_i.mynews.controller.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import neige_i.mynews.R;
import neige_i.mynews.model.Article;
import neige_i.mynews.model.Topic;
import neige_i.mynews.util.NYTStream;
import neige_i.mynews.view.DividerDecoration;
import neige_i.mynews.view.NewsAdapter;
import neige_i.mynews.view.TopicAdapter;
import retrofit2.Response;

import static neige_i.mynews.util.NYTEndpoint.SECTION_BUSINESS;
import static neige_i.mynews.util.NYTEndpoint.SECTION_HOME;
import static neige_i.mynews.view.TopicAdapter.BUSINESS;
import static neige_i.mynews.view.TopicAdapter.MOST_POPULAR;
import static neige_i.mynews.view.TopicAdapter.TOP_STORIES;

/**
 * This fragment displays the RecyclerView containing the article list.<br />
 * It is used in a ViewPager adapter.
 * @see TopicAdapter TopicAdapter
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class ListFragment extends Fragment {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * RecyclerView containing the list of the articles to display.
     */
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    /**
     * Parent layout of this fragment. It is used to correctly implement the Snackbar.
     */
    @BindView(R.id.coordinatorLayout) CoordinatorLayout mCoordinatorLayout;

    // ----------------------------------     DATA VARIABLES     -----------------------------------

    /**
     * List of the articles to show, retrieved from the HTTP request response.
     */
    private final List<Article> mArticleList = new ArrayList<>();

    // ---------------------------------     NETWORK VARIABLES     ---------------------------------

    /**
     * Observer contained in a Disposable object to prevent memory leaks.
     */
    private DisposableObserver mDisposableObserver;

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

        configRecyclerView();
        executeHttpRequest();

        return mainView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeWhenDestroy();
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

    // ----------------------------------     NETWORK METHODS     ----------------------------------

    /**
     * Returns the appropriate Observable according to the specified fragment position.
     * @param fragmentPosition The current position of the displayed fragment.
     * @return The Observable in charge of launching the stream to execute the HTTP request.
     */
    private Observable<? extends Response<? extends Topic>> getObservable(int fragmentPosition) {
        switch (fragmentPosition) {
            case TOP_STORIES:   return NYTStream.streamTopStories(SECTION_HOME);
            case MOST_POPULAR:  return NYTStream.streamMostPopular();
            case BUSINESS:      return NYTStream.streamTopStories(SECTION_BUSINESS);
            default:            throw new IllegalArgumentException("Cannot configure the Observable" +
                    " in ListFragment. This fragment has been instantiated with a wrong 'FRAGMENT_INDEX' argument." +
                    " The found argument is: " + fragmentPosition + '.');
        }
    }

    /**
     * Executes the appropriate request so as to retrieve the article list.
     */
    private void executeHttpRequest() {
        mDisposableObserver = getObservable(getArguments().getInt(FRAGMENT_INDEX))
                .subscribeWith(new DisposableObserver<Response<? extends Topic>>() {
            @Override
            public void onNext(Response<? extends Topic> topicResponse) {
                mArticleList.clear();
                mArticleList.addAll(topicResponse.body().getArticleList());
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                // Display an error message in the Snackbar
                String errorMessage;
                if (e instanceof UnknownHostException)
                    errorMessage = getString(R.string.no_internet);
                else if (e instanceof TimeoutException)
                    errorMessage = getString(R.string.connection_timed_out);
                else
                    errorMessage = getString(R.string.unknown_error);
                Snackbar.make(mCoordinatorLayout, errorMessage, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {}
        });
    }

    /**
     * Disposes the Observable when the activity is destroyed.
     */
    private void disposeWhenDestroy() {
        if (mDisposableObserver != null && !mDisposableObserver.isDisposed())
            mDisposableObserver.dispose();
    }
}
