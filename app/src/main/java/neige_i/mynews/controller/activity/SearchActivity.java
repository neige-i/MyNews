package neige_i.mynews.controller.activity;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import butterknife.BindView;
import neige_i.mynews.R;
import neige_i.mynews.controller.fragment.NotificationFragment;
import neige_i.mynews.controller.fragment.SearchFragment;

/**
 * This activity displays two kinds of content which are very alike: the search and the notification.<br />
 * <b>Search:</b> The user can fill a form to search articles from the New York Times database.
 * This can be done through {@link SearchFragment}.
 * When the search is submitted, the result is shown in
 * {@link neige_i.mynews.controller.fragment.ListFragment ListFragment}.<br />
 * <b>Notification:</b> The user can set criteria to schedule an alarm that will notify him when new
 * articles are published. This can be done through {@link NotificationFragment}.
 * If so, the articles, matching the previously set criteria, are displayed in
 * {@link neige_i.mynews.controller.fragment.ListFragment ListFragment}.
 */
@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class SearchActivity extends ChildActivity {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * Parent layout of this fragment. It is used to correctly implement the Snackbar.
     */
    @BindView(R.id.coordinatorLayout) CoordinatorLayout mCoordinatorLayout;
    // ---------------------------------     STATIC VARIABLES     ----------------------------------

    /**
     * Key to retrieve the kind of content this activity will display.
     * This key can be mapped with only two possible values: {@link #SEARCH_CONTENT} or
     * {@link #NOTIFICATION_CONTENT}.
     */
    public static final String ACTIVITY_CONTENT = "ACTIVITY_CONTENT";

    /**
     * Constant that represents the search content.
     */
    public static final int SEARCH_CONTENT = 0;

    /**
     * Constant that represents the notification content.
     */
    public static final int NOTIFICATION_CONTENT = 1;

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void configUI() {
        // Display the appropriate fragment according to the value passed in the intent extra
        Fragment fragmentToDisplay;
        switch (getIntent().getIntExtra(ACTIVITY_CONTENT, -1)) {
            case SEARCH_CONTENT:        fragmentToDisplay = new SearchFragment();       break;
            case NOTIFICATION_CONTENT:  fragmentToDisplay = new NotificationFragment(); break;
            default:                    throw new IllegalArgumentException("Cannot configure the " +
                    "SearchActivity UI. When starting this activity, an intent extra must be mapped with " +
                    "the 'ACTIVITY_CONTENT' key. This extra can only equal 'SEARCH_CONTENT' or 'NOTIFICATION_CONTENT'.");
        }
        addOrReplaceFragment(fragmentToDisplay, true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        hideSoftKeyboard(event);
        return super.dispatchTouchEvent(event);
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Adds the specified fragment or makes it replace the old one.
     * @param fragmentToDisplay The fragment to be displayed in the activity.
     * @param addOrReplace      True to add the fragment, false to make it replace the old one.
     */
    public void addOrReplaceFragment(Fragment fragmentToDisplay, boolean addOrReplace) {
        // Initialize variables
        int containerId = R.id.fragment_container;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Add or replace fragment
        if (addOrReplace)
            // If the fragment is a new one, then it is SearchFragment
            fragmentTransaction.add(containerId, fragmentToDisplay);
        else {
            // If the fragment replaces an old one, then it is ListFragment
            fragmentTransaction.replace(containerId, fragmentToDisplay).addToBackStack(null);
            setTitle(R.string.search_results);
        }

        // Commit the transaction
        fragmentTransaction.commit();
    }

    /**
     * Hides the soft keyboard that is related to the 'queryInput' EditText.<br />
     * Originally, if the soft keyboard is opened and the user clicks outside of the EditText, the keyboard remains visible.
     * That is because, the EditText is the first view of the layout, and automatically gains focus after losing it.
     * This method simply hides the keyboard when the user clicks outside of it.
     * @param event The event that intercepts the touch action.
     */
    private void hideSoftKeyboard(MotionEvent event) {
        // The touch is intercepted to identify which view is currently focused
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View focusedView;
            if ((focusedView = getCurrentFocus()) != null) {
                // If the focused view is the 'queryInput' EditText and...
                if (focusedView.getId() == R.id.queryInput) {
                    Rect editTextArea = new Rect();
                    focusedView.getGlobalVisibleRect(editTextArea);

                    // ... if the touch does not occur on it...
                    if (!editTextArea.contains((int) event.getRawX(), (int) event.getRawY())) {
                        // ... Then, hide the soft keyboard
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                    }
                }
            }
        }
    }

    /**
     * Returns to the previous screen if no articles were found with the Article Search API request.
     * Also displays a message in a Snackbar.
     */
    public void goBackIfNoArticle() {
        onBackPressed();
        Snackbar.make(mCoordinatorLayout, R.string.no_article_found, Snackbar.LENGTH_LONG).show();
    }
}
