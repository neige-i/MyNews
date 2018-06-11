package neige_i.mynews.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import neige_i.mynews.R;
import neige_i.mynews.controller.activity.SearchActivity;

/**
 * This class is the base fragment for {@link SearchFragment} and {@link NotificationFragment}.
 * The layouts of these two fragments are hardly the same. Both inflate the fragment_search layout.
 * If the fragment to be displayed is NotificationFragment, then another layout (fragment_notification)
 * must be added to the fragment_search one.
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public abstract class BaseFragment extends Fragment {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * EditText displaying the search query terms.
     */
    @BindView(R.id.queryInput) EditText mQueryInput;

    /**
     * CheckBoxes representing the different categories to search.
     */
    @BindViews({ R.id.arts, R.id.business, R.id.entrepreneurs, R.id.politics, R.id.sports, R.id.travel })
    CheckBox[] mCategories;

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_search, container, false);
        addLayout(mainView);
        ButterKnife.bind(this, mainView);

        configUI();

        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getFragmentTitle());
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Checks if the fragment needs to display notification content.
     * If so, the appropriate layout must be inflated.
     * @param rootView The view that will contain the inflated layout.
     */
    private void addLayout(View rootView) {
        if (this instanceof NotificationFragment)
            ((SearchActivity) getActivity()).addContentToLayout(R.layout.fragment_notification, rootView);
    }

    /**
     * Configures the user interface of the fragment.
     */
    protected abstract void configUI();

    /**
     * Gets the id of the String resource that represents the title of the fragment.
     * @return The id of the String resource that will be used as the title of the fragment.
     */
    protected abstract int getFragmentTitle();

    /**
     * Returns a String representation of the checked categories.
     * This String is used as a query parameter in Article Search API request.
     * @return A String representing the selected categories, or null if no one is checked.
     */
    protected String getSelectedCategories() {
        StringBuilder categories = new StringBuilder("news_desk:(");
        for(CheckBox checkBox : mCategories)
            if (checkBox.isChecked())
                categories.append("\"").append(checkBox.getText()).append("\" ");
        categories.append(')'); // If no CheckBox are checked, this String contains 12 characters

        return categories.length() > 12 ? categories.toString() : null;
    }
}
