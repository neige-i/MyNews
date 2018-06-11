package neige_i.mynews.controller.fragment;

import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import butterknife.BindView;
import neige_i.mynews.R;

import static android.view.View.GONE;

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

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    protected int getFragmentTitle() {
        return R.string.notifications;
    }

    @Override
    protected void configUI() {
        initUI();
        configSwitchListener();
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
            }
        });
    }
}
