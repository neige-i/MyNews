package neige_i.mynews.controller;

import android.app.Application;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;

import neige_i.mynews.util.NotificationJob;

import static neige_i.mynews.util.NotificationJob.NOTIFICATION_TAG;

/**
 * This application class is used to create the required job for background tasks.
 */
@SuppressWarnings("WeakerAccess")
public class App extends Application {
    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @Override
    public void onCreate() {
        super.onCreate();
        configBackgroundTask();
    }

    // ------------------------------     BACKGROUND TASK METHODS     ------------------------------

    /**
     * Configures the background task that notifies when new articles are published.
     */
    private void configBackgroundTask() {
        JobManager.create(this).addJobCreator(new JobCreator() {
            @Override
            public Job create(String tag) {
                switch (tag) {
                    case NOTIFICATION_TAG:  return new NotificationJob();
                    default:                return null;
                }
            }
        });
    }
}
