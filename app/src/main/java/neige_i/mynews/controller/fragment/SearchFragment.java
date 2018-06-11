package neige_i.mynews.controller.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import neige_i.mynews.R;
import neige_i.mynews.controller.activity.SearchActivity;

import static neige_i.mynews.controller.fragment.DatePickerFragment.BEGIN_DATE;
import static neige_i.mynews.controller.fragment.DatePickerFragment.CURRENT_DATE;
import static neige_i.mynews.controller.fragment.DatePickerFragment.END_DATE;
import static neige_i.mynews.view.TopicAdapter.ARTICLE_SEARCH;

/**
 * This fragment displays a form to search articles.
 */
@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class SearchFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * Is true if the EditText that has been clicked is {@link #mBeginDateInput}.<br />
     * This fragment contains two EditTexts that contain a date value.
     * This variable is used to correctly initialize the DatePickerFragment that displays the calendar.
     * It is also used to update the EditText's content after setting the date with the calendar.
     */
    private boolean mIsBeginDateInputClicked;

    /**
     * EdiText displaying the date from which the articles must be searched.
     */
    @BindView(R.id.beginDateInput) EditText mBeginDateInput;

    /**
     * EdiText displaying the date until which the articles must be searched.
     */
    @BindView(R.id.endDateInput) EditText mEndDateInput;

    // ----------------------------------     DATA VARIABLES     -----------------------------------

    /**
     * Date format to parse String into Date and to format Date into String.
     */
    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    protected int getFragmentTitle() {
        return R.string.search_articles;
    }

    @Override
    protected void configUI() {}

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // For API <=19: this method is still called even if the dialog is cancelled
        // The 'if' statement below is used to prevent this scenario
        if (view.isShown()) {
            // Get the selected date from the date picker
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);

            // Update the EditText content after choosing the date in the picker
            (mIsBeginDateInputClicked ? mBeginDateInput : mEndDateInput).setText(mDateFormat.format(selectedDate.getTime()));
        }
    }

    // ----------------------------------     ACTION METHODS     -----------------------------------

    /**
     * Called when an EditText is clicked.
     * @param clickedInput The editText that received the click event.
     */
    @OnClick({R.id.beginDateInput, R.id.endDateInput})
    void openDatePicker(EditText clickedInput) {
        // Identify the EditText that received the click event
        mIsBeginDateInputClicked = clickedInput.getId() == R.id.beginDateInput;

        // Configure the bundle
        // If the begin (respectively the end) DatePicker is displayed,
        // its maxDate (respectively minDate) must not be greater (respectively lower)
        // than the currentDate of the end (respectively begin) DatePicker
        Bundle args = new Bundle();
        try {
            String key = mIsBeginDateInputClicked ? END_DATE : BEGIN_DATE;
            EditText input = mIsBeginDateInputClicked ? mEndDateInput : mBeginDateInput;
            args.putSerializable(key, getDateFromEditText(input));
            args.putSerializable(CURRENT_DATE, getDateFromEditText(clickedInput));
        } catch (ParseException ignored) {}

        // Show the date picker dialog
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(args);
        datePickerFragment.setTargetFragment(this, 0);
        datePickerFragment.show(getActivity().getSupportFragmentManager(), "");
    }

    /**
     * Called when the Button is clicked.
     */
    @OnClick(R.id.button)
    void submitSearch() {
        // Get the query terms
        String query = mQueryInput.getText().toString();

        // Get the selected categories
        StringBuilder categories = new StringBuilder("news_desk:(");
        for(CheckBox checkBox : mCategories)
            if (checkBox.isChecked())
                categories.append("\"").append(checkBox.getText()).append("\" ");
        categories.append(')'); // If no CheckBox is checked, this String contains 12 characters

        View popupView = getPopupView(query, categories.toString());
        if (popupView != null)
            // Show a popup message if there is an error in the search form
            new AlertDialog.Builder(getActivity()).setView(popupView).show();
        else {
            // Otherwise, execute the search request
            String[] searchParameters = new String[] {query, changeDateFormat(mBeginDateInput), changeDateFormat(mEndDateInput), categories.toString()};
            ((SearchActivity) getActivity()).addOrReplaceFragment(ListFragment.newInstance(ARTICLE_SEARCH, searchParameters), false);
        }
    }

    // -----------------------------------     DATA METHODS     ------------------------------------

    /**
     * Converts the content of the EditText into a Date. If its content is an empty String, this returns null.
     * @param editText The EditText that contains the String to parse into Date.
     * @return A Date if the String contained in the EditText is not empty, null otherwise.
     * @throws ParseException If the EditText's content cannot be parsed.
     */
    private Date getDateFromEditText(EditText editText) throws ParseException {
        String date = editText.getText().toString();
        return !date.isEmpty() ? mDateFormat.parse(date) : null;
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Configures the message that would be shown if the mandatory fields are not filled out.
     * Null is returned if the fields are correctly filled out.
     * @param queryTerms         The query terms representing the key words for the article search.
     * @param selectedCategories The categories which the article must be search in.
     * @return  The TextView containing the error message if the fields are not correctly filled out.
     *          False otherwise.
     */
    @SuppressLint("InflateParams")
    private TextView getPopupView(String queryTerms, String selectedCategories) {
        // Set the error message
        String errorMessage = "";
        if (queryTerms.isEmpty()) // If no query term is input
            errorMessage += "- " + getString(R.string.query_field_mandatory);
        if (selectedCategories.length() == 12) { // If no category is selected
            if (!errorMessage.isEmpty())
                errorMessage += "\n\n"; // Add a new line in the error message if there is more than 1 error
            errorMessage += "- " + getString(R.string.category_field_mandatory);
        }

        // Return a TextView if the error message is not empty
        TextView popupView = null;
        if (!errorMessage.isEmpty()) {
            popupView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.dialog_error, null);
            popupView.setText(errorMessage);
        }
        return popupView;
    }

    /**
     * Changes the format of the date contained in EditText.<br />
     * The "dd/MM/yyyy" format (used when the date is displayed in an EditText) must be changed to
     * "yyyyMMdd" because the latter is used in the HTTP request address.
     * @param editText The String in the old date format.
     * @return  The String in the new date format, or null if the EditText's content is empty.
     */
    private String changeDateFormat(EditText editText) {
        String oldDateFormat = editText.getText().toString();
        if (oldDateFormat.isEmpty())
            return null;
        else
            try { // String -> (parse) -> Date -> (format) -> String
                return new SimpleDateFormat("yyyyMMdd", Locale.US).format(mDateFormat.parse(oldDateFormat));
            } catch (ParseException e) {
                return null;
            }
    }
}
