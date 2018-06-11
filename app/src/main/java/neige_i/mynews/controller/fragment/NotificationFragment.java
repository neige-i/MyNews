package neige_i.mynews.controller.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

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

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    protected int getFragmentTitle() {
        return R.string.notifications;
    }

    @Override
    protected void configUI() {
        mPreferences = getActivity().getPreferences(MODE_PRIVATE);

        initUI();
        configSwitchListener();
    }

    @Override
    public void onStop() {
        super.onStop();
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
    }

    /**
     * Configures the actions to perform when the switch state changes.
     */
    private void configSwitchListener() {
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNotificationText.setTextColor(isChecked ? Color.BLACK : 0xff808080);
                if (!isChecked)
                    cancelNotification(mPreferences.getInt(JOB_ID, -1));
            }
        });
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
}
