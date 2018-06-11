package neige_i.mynews.util;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.observers.DisposableObserver;
import neige_i.mynews.R;
import neige_i.mynews.controller.activity.SearchActivity;
import neige_i.mynews.model.Article;
import neige_i.mynews.model.Topic;
import retrofit2.Response;

import static neige_i.mynews.controller.activity.SearchActivity.ACTIVITY_CONTENT;
import static neige_i.mynews.controller.activity.SearchActivity.NOTIFICATION_CONTENT;

/**
 * This Job schedules a daily alarm to notify if new articles were published.
 */
public class NotificationJob extends DailyJob {
    // ---------------------------------     STATIC VARIABLES     ----------------------------------

    /**
     * Tag of the job that notify about newly published articles.
     */
    public static final String NOTIFICATION_TAG = "NEW_ARTICLES_NOTIFICATION";

    /**
     * Key to retrieve the query parameters for the API request.
     */
    private static final String SEARCH_PARAMETERS = "SEARCH_PARAMETERS";

    /**
     * Key to retrieve the titles of the already read articles.
     */
    private static final String READ_ARTICLE_TITLES = "READ_ARTICLE_TITLES";

    // ----------------------------------     STATIC METHODS     -----------------------------------

    /**
     * Schedules a daily job between 8am and 10am.
     * @param searchParameters   Parameters that represent the search criterion for the notification.
     * @param readArticleTitles The list of the read articles. If the user has already read the first
     *                          article of the list that would be notified, do not launch it.
     * @return The id of the notification job.
     */
    public static int scheduleNotification(String[] searchParameters, String[] readArticleTitles) {
        // Set the bundle
        PersistableBundleCompat extras = new PersistableBundleCompat();
        extras.putStringArray(SEARCH_PARAMETERS, searchParameters);
        extras.putStringArray(READ_ARTICLE_TITLES, readArticleTitles);

        // Set the builder
        JobRequest.Builder builder = new JobRequest.Builder(NOTIFICATION_TAG)
                .setUpdateCurrent(true)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .setExtras(extras);

        // Schedule the notification job between 8am and 10am
        return DailyJob.schedule(builder, TimeUnit.HOURS.toMillis(8), TimeUnit.HOURS.toMillis(10));
    }

    /**
     * Cancels the job with the specified id.
     * @param jobId The id of the job to cancel.
     */
    public static void cancelNotification(int jobId) {
        JobManager.instance().cancel(jobId);
    }

    // --------------------------------     OVERRIDDEN METHODS     ---------------------------------

    @SuppressLint("CheckResult")
    @Override
    protected DailyJobResult onRunDailyJob(Params params) {
        // Retrieve extras from the bundle
        final String[] searchParameters = params.getExtras().getStringArray(SEARCH_PARAMETERS);
        final List<String> titles = Arrays.asList(params.getExtras().getStringArray(READ_ARTICLE_TITLES));

        // Execute the HTTP request
        NYTStream.streamArticleSearch(searchParameters[0],searchParameters[1], searchParameters[2], searchParameters[3])
                .subscribeWith(new DisposableObserver<Response<? extends Topic>>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onNext(Response<? extends Topic> response) {
                List<Article> articleList = response.body().getArticleList();
                // If the article list is not empty and if the first one was not already read
                if (!articleList.isEmpty() && !titles.contains(articleList.get(0).getTitle()))
                    configNotification(searchParameters);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        });
        return DailyJobResult.SUCCESS;
    }

    // -------------------------------     NOTIFICATION METHODS     --------------------------------

    /**
     * Configures the notification.
     * @param searchParameters The search parameters that define how to search articles for the notification.
     */
    @SuppressWarnings("ConstantConditions")
    private void configNotification(String[] searchParameters) {
        // Initialize variables
        String channelId = getContext().getString(R.string.channel_id);
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Set the channel and the importance for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, getContext().getString(R.string.channel_name), NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Set the notification's tap action, with the intent of the activity to start...
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtra(ACTIVITY_CONTENT, NOTIFICATION_CONTENT);
        intent.putExtra(SEARCH_PARAMETERS, searchParameters);

        // ... and with the TaskStackBuilder which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        stackBuilder.addNextIntentWithParentStack(intent);

        // ... and finally with the PendingIntent containing the whole back stack
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the notification content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId)
                .setSmallIcon(R.drawable.ic_star_white)
                .setContentTitle(getContext().getString(R.string.notification_title))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        notificationManager.notify(0, builder.build());
    }
}
