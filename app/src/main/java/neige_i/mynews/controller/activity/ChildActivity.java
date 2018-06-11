package neige_i.mynews.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import neige_i.mynews.R;

/**
 * This class is the base class for children activities of MainActivity.
 * Its defines their general UI and behaviour.
 */
@SuppressWarnings("WeakerAccess")
public abstract class ChildActivity extends AppCompatActivity {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * Activity's toolbar.
     */
    @BindView(R.id.toolbar) Toolbar mToolbar;

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        addContentToLayout(getLayoutId(), findViewById(R.id.rootView));
        // Binding views must occur after completely inflating the layout to prevent throwing
        // IllegalStateException. Thus, the previous method must be called before ButterKnife.bind().
        // But, this method needs a root view which cannot be annotated with @BindView.
        // Indeed a @BindView variable would be null since it has not been bound yet with
        // ButterKnife.bind(). That is why findViewById() is used here instead of a @BindView variable.
        ButterKnife.bind(this);

        configToolbar();
        configUI();
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
     * Inflates a view with its id to the specified root view.
     * As the content view only defines the base layout, this method is in charge of inflating the
     * appropriate layout file so as to get the whole UI.
     * @param contentToAdd The id of the layout resource file to inflate.
     * @param root         The root view that will contain the inflated one.
     */
    public void addContentToLayout(int contentToAdd, View root) {
        View.inflate(this, contentToAdd, (ViewGroup) root);
    }

    /**
     * Gets the id of the layout resource file to be inflated in {@link #addContentToLayout(int, View)}.
     * @return  The id of the layout resource file that will be used when inflating it to the activity.
     * @see #addContentToLayout(int, View)
     */
    protected abstract int getLayoutId();

    /**
     * Configures the activity Toolbar.
     */
    @SuppressWarnings("ConstantConditions")
    protected void configToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Configures the user interface of the activity.
     */
    protected abstract void configUI();
}
