package neige_i.mynews.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import neige_i.mynews.R;
import neige_i.mynews.controller.fragment.ListFragment;

/**
 * This PagerAdapter allows the user to swipe between the different topics.
 * Each topic represents a type of news to display.<br />
 * The content of its pages is defined in a fragment.
 * @see ListFragment
 */
public class TopicAdapter extends FragmentPagerAdapter {
    // --------------------------------     INSTANCE VARIABLES     ---------------------------------

    /**
     * Context to access the array resources that contains the page titles.
     * @see #getPageTitle(int)
     */
    private final Context mContext;

    // ---------------------------------     STATIC VARIABLES     ----------------------------------

    /**
     * Index of the page displaying the top stories.
     */
    public static final int TOP_STORIES = 0;

    /**
     * Index of the page displaying the most popular articles.
     */
    public static final int MOST_POPULAR = 1;

    /**
     * Index of the page displaying the business articles.
     */
    public static final int BUSINESS = 2;

    // -----------------------------------     CONSTRUCTORS     ------------------------------------

    public TopicAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public Fragment getItem(int position) {
        return ListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.tab_title)[position];
    }
}
