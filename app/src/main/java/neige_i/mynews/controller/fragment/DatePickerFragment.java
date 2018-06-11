package neige_i.mynews.controller.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import neige_i.mynews.R;

/**
 * This fragment displays the date picker dialog.
 * This dialog allows user to choose both begin and end dates.
 * These dates represent the period of time in which the articles must be searched.
 */
@SuppressWarnings("ConstantConditions")
public class DatePickerFragment extends DialogFragment {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * Dialog to display.
     */
    private DatePickerDialog mDatePickerDialog;

    // ----------------------------------     DATA VARIABLES     -----------------------------------

    /**
     * Date from which the articles must be searched.
     */
    private Calendar mBeginDate;

    /**
     * Date at which the picker is initialized.
     */
    private Calendar mCurrentDate;

    /**
     * Date until which the articles must be searched.
     */
    private Calendar mEndDate;

    // ---------------------------------     STATIC VARIABLES     ----------------------------------

    /**
     * Key to retrieve the begin date.
     */
    public static final String BEGIN_DATE = "BEGIN_DATE";

    /**
     * Key to retrieve the current date.
     */
    public static final String CURRENT_DATE = "CURRENT_DATE";

    /**
     * Key to retrieve the end date.
     */
    public static final String END_DATE = "END_DATE";

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        configDates();
        configDialog();
        configPicker();
        return mDatePickerDialog;
    }

    // -----------------------------------     DATA METHODS     ------------------------------------

    /**
     * Configures the dates.
     */
    private void configDates() {
        // Initialize the dates
        mBeginDate = Calendar.getInstance();
        mBeginDate.set(1851, Calendar.SEPTEMBER, 18); // Date of the first published article
        mCurrentDate = Calendar.getInstance();
        mEndDate = Calendar.getInstance();

        // Retrieve the arguments from the bundle
        Date beginDate = (Date) getArguments().getSerializable(BEGIN_DATE),
                currentDate = (Date) getArguments().getSerializable(CURRENT_DATE),
                endDate = (Date) getArguments().getSerializable(END_DATE);

        // Set the dates according to the arguments
        if (beginDate != null)
            mBeginDate.setTime(beginDate);
        if (currentDate != null)
            mCurrentDate.setTime(currentDate);
        if (endDate != null)
            mEndDate.setTime(endDate);
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Configures the dialog.
     */
    private void configDialog() {
        mDatePickerDialog = new DatePickerDialog(
                getContext(),
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.style.widgetTheme : 0,
                (DatePickerDialog.OnDateSetListener) getTargetFragment(),
                mCurrentDate.get(Calendar.YEAR),
                mCurrentDate.get(Calendar.MONTH),
                mCurrentDate.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Configures the picker.
     */
    private void configPicker() {
        DatePicker datePicker = mDatePickerDialog.getDatePicker();
        datePicker.setMinDate(mBeginDate.getTimeInMillis());
        datePicker.setMaxDate(mEndDate.getTimeInMillis());
    }
}
