package neige_i.mynews.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import neige_i.mynews.R;
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
     * TextView displaying which fragment is currently showed.
     */
    @BindView(R.id.tempTextView) TextView mTempTextView;

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

        configTextView();

        return mainView;
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Configures the TextView.
     */
    private void configTextView() {
        assert getArguments() != null;
        mTempTextView.setText(String.valueOf(getArguments().getInt(FRAGMENT_INDEX)));
    }
}
