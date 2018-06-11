package neige_i.mynews.controller.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import neige_i.mynews.R;
import neige_i.mynews.controller.fragment.SearchFragment;

/**
 * This activity displays a form to search articles from the New York Times database.
 * This can be done inside {@link SearchFragment}.
 * When the search is submitted, the result is shown in
 * {@link neige_i.mynews.controller.fragment.ListFragment ListFragment}.
 */
@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class SearchActivity extends AppCompatActivity {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * Activity's toolbar.
     */
    @BindView(R.id.toolbar) Toolbar mToolbar;

    /**
     * Parent layout of this fragment. It is used to correctly implement the Snackbar.
     */
    @BindView(R.id.coordinatorLayout) CoordinatorLayout mCoordinatorLayout;

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        configToolbar();
        addOrReplaceFragment(new SearchFragment(), true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        hideSoftKeyboard(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // There is only one item in the ToolBar: the 'up' navigation button
        // So, the user should be taken to the parent activity when this button is clicked
        onBackPressed();
        return true;
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Configures the activity Toolbar.
     */
    private void configToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
