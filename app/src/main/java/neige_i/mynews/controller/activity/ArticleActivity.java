package neige_i.mynews.controller.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import neige_i.mynews.R;

/**
 * This activity displays the article in a WebView.
 */
@SuppressWarnings("WeakerAccess")
public class ArticleActivity extends ChildActivity {
    // -----------------------------------     UI VARIABLES     ------------------------------------

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
    protected int getLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void configToolbar() {
        super.configToolbar();
        getSupportActionBar().setTitle(getIntent().getStringExtra(TITLE));
    }

    @Override
    protected void configUI() {
        configWebView();
    }

    @Override
    public void onBackPressed() {
        if (!canWebViewGoesBack()) super.onBackPressed();
    }

    // ------------------------------------     UI METHODS     -------------------------------------

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

    /**
     * Goes back in the WebView history.
     * @return true if the WebView actually goes back in its history, false otherwise.
     */
    private boolean canWebViewGoesBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else
            return false;
    }
}
