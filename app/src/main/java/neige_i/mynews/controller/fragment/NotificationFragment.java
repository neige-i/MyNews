package neige_i.mynews.controller.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import neige_i.mynews.R;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static neige_i.mynews.controller.fragment.ListFragment.DIVIDER;
import static neige_i.mynews.controller.fragment.ListFragment.READ_ARTICLE;
import static neige_i.mynews.util.NotificationJob.cancelNotification;
import static neige_i.mynews.util.NotificationJob.scheduleNotification;

/**
 * This fragment displays the notification settings.
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class NotificationFragment extends BaseFragment {
    // -----------------------------------     UI VARIABLES     ------------------------------------

    /**
     * Layout to hide.
     * It is displaying EditText for selecting dates and therefore is useless in this fragment.
     */
    @BindView(R.id.dateLayout) View mDateLayout;

    /**
     * Button to hide.
     * It is replaced by the SwitchCompat and therefore is useless in this fragment.
     */
    @BindView(R.id.button) Button mButton;

    /**
     * TextView associated with {@link #mSwitchCompat}.
     */
    @BindView(R.id.notificationText) TextView mNotificationText;

    /**
     * Switch enabling and disabling the notification.
     */
    @BindView(R.id.notificationSwitch) SwitchCompat mSwitchCompat;

    // ----------------------------------     DATA VARIABLES     -----------------------------------

    /**
     * Preferences that store information about this fragment.
     */
    private SharedPreferences mPreferences;

    /**
     * Key to store the id of the notification job.
     */
    private final String JOB_ID = "JOB_ID";

    /**
     * Key to store the query terms into preferences.
     */
    private final String QUERY_TERM = "QUERY_TERM";

    /**
     * Key to store the checked categories into preferences.
     */
    private final String CATEGORIES = "CATEGORIES";

    /**
     * Key to store the notification enable state into preferences.
     */
    private final String NOTIFICATION_ENABLE = "NOTIFICATION_ENABLE";

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    protected int getFragmentTitle() {
        return R.string.notifications;
    }

    @Override
    protected void configUI() {
        mPreferences = getActivity().getPreferences(MODE_PRIVATE);

        initUI();
        initEnableState();
        configSwitchListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveState();
        if (mSwitchCompat.isChecked())
            startNotification();
    }

    // ------------------------------------     UI METHODS     -------------------------------------

    /**
     * Initializes the UI.
     */
    private void initUI() {
        // These views are not used in this fragment
        mDateLayout.setVisibility(GONE);
        mButton.setVisibility(GONE);

        // Initialize the EditText
        mQueryInput.setText(mPreferences.getString(QUERY_TERM, ""));

        // Initialize the CheckBoxes
        List<String> categoryList = Arrays.asList(mPreferences.getString(CATEGORIES, "").split(DIVIDER));
        for (CheckBox checkBox : mCategories)
            checkBox.setChecked(categoryList.contains(checkBox.getText().toString()));
    }

    /**
     * Initializes the widgets' enable state from preferences.
     */
    private void initEnableState() {
        boolean isNotificationEnabled = mPreferences.getBoolean(NOTIFICATION_ENABLE, false);
        mSwitchCompat.setChecked(isNotificationEnabled);
        configWidgetEnableState(isNotificationEnabled);
    }

    /**
     * Configures the actions to perform when the switch state changes.
     */
    private void configSwitchListener() {
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                configWidgetEnableState(isChecked);
                if (!isChecked)
                    cancelNotification(mPreferences.getInt(JOB_ID, -1));
            }
        });
    }

    /**
     * Configures the widgets' enable state.
     * @param isNotificationEnabled True if notification is enabled, false otherwise.
     */
    private void configWidgetEnableState(boolean isNotificationEnabled) {
        mNotificationText.setTextColor(isNotificationEnabled ? Color.BLACK : 0xff808080);
        mQueryInput.setEnabled(isNotificationEnabled);
        for (CheckBox checkBox : mCategories)
            checkBox.setEnabled(isNotificationEnabled);
    }

    // --------------------------------     BACKGROUND METHODS     ---------------------------------

    /**
     * Starts the job which takes care of sending notifications.
     */
    private void startNotification() {
        // Launch the background task with Android-Job
        int jobId = scheduleNotification(new String[] {
                mQueryInput.getText().toString(),
                null,
                null,
                getSelectedCategories()
        }, getActivity().getSharedPreferences(ListFragment.ARTICLE_FILE, MODE_PRIVATE).getString(READ_ARTICLE, "").split(DIVIDER));

        // Save the job id in the preferences
        mPreferences.edit().putInt(JOB_ID, jobId).apply();
    }

    // -----------------------------------     DATA METHODS     ------------------------------------

    /**
     * Saves the form state.<br />
     * If the user leaves this fragment with filled fields and comes back to it later,
     * he will not fill the form again.
     */
    private void saveState() {
        StringBuilder selectedCategories = new StringBuilder();
        for (CheckBox checkBox : mCategories)
            if (checkBox.isChecked())
                selectedCategories.append(checkBox.getText()).append(DIVIDER);

        mPreferences.edit()
                .putString(QUERY_TERM, mQueryInput.getText().toString())
                .putString(CATEGORIES, selectedCategories.toString())
                .putBoolean(NOTIFICATION_ENABLE, mQueryInput.isEnabled())
                .apply();
    }
}
