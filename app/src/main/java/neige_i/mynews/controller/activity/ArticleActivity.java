package neige_i.mynews.controller.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import neige_i.mynews.R;

/**
 * This activity displays the article in a WebView.
 */
@SuppressWarnings("WeakerAccess")
public class ArticleActivity extends AppCompatActivity {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * Activity's toolbar.
     */
    @BindView(R.id.toolbar) Toolbar mToolbar;

    /**
     * WebView displaying the article content.
     */
    @BindView(R.id.webView) WebView mWebView;

    /**
     * ProgressBar that is displayed while the web page is loading.
     */
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    // ---------------------------------     STATIC VARIABLES     ----------------------------------

    /**
     * Key to retrieve the title of the toolbar from the intent extra.
     */
    public static final String TITLE = "TITLE";

    /**
     * Key to retrieve the URL address of the article to display from the intent extra.
     */
    public static final String URL = "URL";

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        configToolbar();
        configWebView();
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
    @SuppressWarnings("ConstantConditions")
    private void configToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(TITLE));
    }

    /**
     * Configures the WebView.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void configWebView() {
        // The WebViewClient forces the WebView to display the web content "in app" and not opening
        // a web browser. It also displays a ProgressBar until the WebView finishes loading the page.
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }
        });

        // Enable JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);

        // Load the URL
        mWebView.loadUrl(getIntent().getStringExtra(URL));
    }
}
