package neige_i.mynews.controller.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import neige_i.mynews.R;

import static neige_i.mynews.controller.fragment.DatePickerFragment.BEGIN_DATE;
import static neige_i.mynews.controller.fragment.DatePickerFragment.CURRENT_DATE;
import static neige_i.mynews.controller.fragment.DatePickerFragment.END_DATE;

/**
 * This fragment displays a form to search articles.
 */
@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class SearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * EditText displaying the search query terms.
     */
    @BindView(R.id.queryInput) EditText mQueryInput;

    /**
     * Id of the EditText that has been clicked (either {@link #mBeginDateInput} or {@link #mEndDateInput}).
     */
    private int mEditTextId;

    /**
     * EdiText displaying the date from which the articles must be searched.
     */
    @BindView(R.id.beginDateInput) EditText mBeginDateInput;

    /**
     * EdiText displaying the date until which the articles must be searched.
     */
    @BindView(R.id.endDateInput) EditText mEndDateInput;

    /**
     * CheckBoxes representing the different categories to search.
     */
    @BindViews({ R.id.arts, R.id.business, R.id.entrepreneurs, R.id.politics, R.id.sports, R.id.travel })
    CheckBox[] mCategories;

    /**
     * Button to submit the search operation.
     */
    @BindView(R.id.button) Button mButton;

    // ----------------------------------     DATA VARIABLES     -----------------------------------

    /**
     * Date format to parse String into Date and to format Date into String.
     */
    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, mainView);

        configClickListener();

        return mainView;
    }

    @Override
    public void onClick(View v) {
        openDatePicker((EditText) v);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // For API <=19: this method is still called even if the dialog is cancelled
        // The 'if' statement below is used to prevent this scenario
        if (view.isShown()) {
            // Get the selected date from the date picker
            Calendar selectedDay = Calendar.getInstance();
            selectedDay.set(year, month, dayOfMonth);
            Date selectedDate = selectedDay.getTime();

            // Update the EditText content after choosing the date in the picker
            EditText clickedEditText;
            if (mEditTextId == R.id.beginDateInput)
                clickedEditText = mBeginDateInput;
            else
                clickedEditText = mEndDateInput;
            clickedEditText.setText(mDateFormat.format(selectedDate));
        }
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Configures the click listener for EditTexts.
     */
    private void configClickListener() {
        mBeginDateInput.setOnClickListener(this);
        mEndDateInput.setOnClickListener(this);
    }

    /**
     * Called when an EditText is clicked.
     * @param editText The editText that received the click event.
     */
    private void openDatePicker(EditText editText) {
        // Get the id and the text of the EditText that received the click event
        mEditTextId = editText.getId();
        String currentDateText = editText.getText().toString();

        // Configure the bundle
        // If the begin (respectively the end) DatePicker is displayed,
        // its maxDate (respectively minDate) must not be greater (respectively lower)
        // than the currentDate of the end (respectively begin) DatePicker
        Bundle args = new Bundle();
        try {
            args.putSerializable(CURRENT_DATE, getDateFromString(currentDateText));
            if (mEditTextId == R.id.beginDateInput)
                args.putSerializable(END_DATE, getDateFromString(mEndDateInput.getText().toString()));
            else
                args.putSerializable(BEGIN_DATE, getDateFromString(mBeginDateInput.getText().toString()));
        } catch (ParseException ignored) {}

        // Show the date picker dialog
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(args);
        datePickerFragment.setTargetFragment(this, 0);
        datePickerFragment.show(getActivity().getSupportFragmentManager(), "");
    }

    private Date getDateFromString(String date) throws ParseException {
        if (date.isEmpty())
            return null;
        else
            return mDateFormat.parse(date);
    }
}
