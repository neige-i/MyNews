package neige_i.mynews.controller.activity;

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
            case R.id.search:           Log.i("MainActivity's menu", "click on 'Search'");          return true;
            case R.id.notifications:    Log.i("MainActivity's menu", "click on 'Notifications'");   return true;
            case R.id.about:            Log.i("MainActivity's menu", "click on 'About'");           return true;
            default:                    return super.onOptionsItemSelected(item);
        }
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Configures the activity Toolbar.
     */
    private void configToolbar() {
        assert getSupportActionBar() != null;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setElevation(0); // The TabLayout is just below the Toolbar
    }

    /**
     * Configures the ViewPager.
     */
    private void configViewPager() {
        mViewPager.setAdapter(new TopicAdapter(getSupportFragmentManager(), this));
    }
}
