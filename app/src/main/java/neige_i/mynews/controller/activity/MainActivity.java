package neige_i.mynews.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import neige_i.mynews.R;
import neige_i.mynews.view.TopicAdapter;

import static android.support.v4.view.GravityCompat.START;
import static neige_i.mynews.controller.activity.SearchActivity.ACTIVITY_CONTENT;
import static neige_i.mynews.controller.activity.SearchActivity.NOTIFICATION_CONTENT;
import static neige_i.mynews.controller.activity.SearchActivity.SEARCH_CONTENT;
import static neige_i.mynews.view.TopicAdapter.BUSINESS;
import static neige_i.mynews.view.TopicAdapter.MOST_POPULAR;
import static neige_i.mynews.view.TopicAdapter.TOP_STORIES;

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

    /**
     * Layout to implement the Navigation Drawer.
     */
    @BindView(R.id.drawerLayout) DrawerLayout mDrawerLayout;

    /**
     * View containing the Navigation Drawer items.
     */
    @BindView(R.id.navigation_view) NavigationView mNavigationView;

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configToolbar();
        configViewPager();
        configDrawerLayout();
        configNavigationView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNavigationView();
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

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(START))
            mDrawerLayout.closeDrawer(START);
        else
            super.onBackPressed();
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
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // Change the selected item in Navigation Drawer when swiping the ViewPager
                mNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
    }

    /**
     * Configures the DrawerLayout.
     */
    private void configDrawerLayout() {
        // Set the listener
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.open_navigation_drawer,
                R.string.close_navigation_drawer
        );

        // Add the listener to the DrawerLayout
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Configures the NavigationView by setting the actions to perform if a drawer item is selected.
     */
    private void configNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.topStories:       displayTopic(TOP_STORIES);              break;
                    case R.id.mostPopular:      displayTopic(MOST_POPULAR);             break;
                    case R.id.business:         displayTopic(BUSINESS);                 break;
                    case R.id.searchArticle:    startActivity(SEARCH_CONTENT);          break;
                    case R.id.notification:     startActivity(NOTIFICATION_CONTENT);    break;
                }
                mDrawerLayout.closeDrawer(START);
                return true;
            }
        });
    }

    /**
     * Initializes the NavigationView by selecting the appropriate item at application (re)start.
     */
    private void initNavigationView() {
        mNavigationView.getMenu().getItem(mViewPager.getCurrentItem()).setChecked(true);
    }

    /**
     * Displays the topic at the specified position.
     * @param position The position of the topic to display.
     */
    private void displayTopic(int position) {
        mViewPager.setCurrentItem(position);
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
