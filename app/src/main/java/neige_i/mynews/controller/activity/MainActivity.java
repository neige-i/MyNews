package neige_i.mynews.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import neige_i.mynews.R;
import neige_i.mynews.view.TopicAdapter;

import static neige_i.mynews.controller.activity.SearchActivity.ACTIVITY_CONTENT;
import static neige_i.mynews.controller.activity.SearchActivity.NOTIFICATION_CONTENT;
import static neige_i.mynews.controller.activity.SearchActivity.SEARCH_CONTENT;

/**
 * This activity displays the news through ViewPager and TabLayout.
 * Each page of the ViewPager contains a different news topic.
 */
@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * Activity's toolbar.
     */
    @BindView(R.id.toolbar) Toolbar mToolbar;

    /**
     * ViewPager charged with displaying the news.
     */
    @BindView(R.id.viewPager) ViewPager mViewPager;

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configToolbar();
        configViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:           startActivity(SEARCH_CONTENT);                              return true;
            case R.id.notifications:    startActivity(NOTIFICATION_CONTENT);                        return true;
            case R.id.about:            Log.i("MainActivity's menu", "click on 'About'");           return true;
            default:                    return super.onOptionsItemSelected(item);
        }
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Configures the activity Toolbar.
     */
    @SuppressWarnings("ConstantConditions")
    private void configToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setElevation(0); // The TabLayout is just below the Toolbar
    }

    /**
     * Configures the ViewPager.
     */
    private void configViewPager() {
        mViewPager.setAdapter(new TopicAdapter(getSupportFragmentManager(), this));
    }

    /**
     * Starts an activity to search articles or set the notifications.
     * The whichFragment value determines which fragment to display.
     * @param whichFragment Key to choose which fragment to display when the activity is started.
     *                      Must be either {@link SearchActivity#SEARCH_CONTENT SEARCH_CONTENT} or
     *                      {@link SearchActivity#NOTIFICATION_CONTENT NOTIFICATION_CONTENT}.
     */
    private void startActivity(int whichFragment) {
        Intent activityIntent = new Intent(this, SearchActivity.class);
        activityIntent.putExtra(ACTIVITY_CONTENT, whichFragment);
        startActivity(activityIntent);
    }
}
