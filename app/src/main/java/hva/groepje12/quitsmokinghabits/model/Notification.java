package hva.groepje12.quitsmokinghabits.model;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import hva.groepje12.quitsmokinghabits.R;
import hva.groepje12.quitsmokinghabits.ui.activity.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notification {

    private String title, text;
    private Context context;
    private Class<?> internDestination;
    private Intent intentDestination;

    public Notification(String title, String text, Class<?> internDestination, Context context) {
        this.title = title;
        this.text = text;
        this.context = context;
        this.internDestination = internDestination;
    }

    public Notification(String title, String text, Intent intentDestination, Context context) {
        this.title = title;
        this.text = text;
        this.context = context;
        this.intentDestination = intentDestination;
    }

    public void startNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSmallIcon(R.drawable.cig)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{200, 200, 200, 200})
                        .setLights(Color.CYAN, 3000, 3000);

        Intent resultIntent = new Intent(context, MainActivity.class);

        if (intentDestination != null) {
            resultIntent = intentDestination;
        }

        if (internDestination != null) {
            resultIntent = new Intent(context, internDestination);
        }

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(001, mBuilder.build());

    }

    public void setContext(Context context) {
        this.context = context;
    }

}
