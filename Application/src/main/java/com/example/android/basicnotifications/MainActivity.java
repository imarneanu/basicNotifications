package com.example.android.basicnotifications;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

/**
 * The entry point to the BasicNotification sample.
 */
public class MainActivity extends Activity {
    /**
     * A numeric value that identifies the notification that we'll be sending.
     * This value needs to be unique within this app, but it doesn't need to be
     * unique system-wide.
     */
    public static final int BASIC_NOTIFICATION = 1;
    public static final int MAP_INTENT_NOTIFICATION = 2;
    public static final int BACKGROUND_NOTIFICATION = 3;
    public static final int EXPANDABLE_NOTIFICATION = 4;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_layout);
    }

    /**
     * Send a sample notification using the NotificationCompat API.
     */
    public void sendBasicNotification(View view) {

        // BEGIN_INCLUDE(build_action)
        /** Create an intent that will be fired when the user clicks the notification.
         * The intent needs to be packaged into a {@link android.app.PendingIntent} so that the
         * notification service can fire it on our behalf.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // END_INCLUDE(build_action)

        // BEGIN_INCLUDE (build_notification)
        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.drawable.ic_stat_notification);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        /**
         *Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

        /**
         * Set the text of the notification. This sample sets the three most commononly used
         * text areas:
         * 1. The content title, which appears in large type at the top of the notification
         * 2. The content text, which appears in smaller text below the title
         * 3. The subtext, which appears under the text on newer devices. Devices running
         *    versions of Android prior to 4.2 will ignore this field, so don't use it for
         *    anything vital!
         */
        builder.setContentTitle("BasicNotifications Sample");
        builder.setContentText("Time to learn about notifications!");
        builder.setSubText("Tap to view documentation about notifications.");
        // END_INCLUDE (build_notification)

        // BEGIN_INCLUDE(send_notification)
        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(
                getApplicationContext());
        notificationManager.notify(BASIC_NOTIFICATION, builder.build());
        // END_INCLUDE(send_notification)
    }

    public void sendMapIntentNotification(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle("MapIntentNotifications Sample")
                .setContentText("Time to learn about actions!")
                .setSubText("Tap to view documentation about notifications.");

        // Build an intent for an action to view a map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:46.771210,23.623635");
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 0, mapIntent, 0);

        /**
         * Phone notification action
         * - visible both on phone and wearable automatically
         * - the intents run on the phone, not the wearable
         * Note: visible on the Wear as long as there is no WearableExtender set
         */
        builder.addAction(android.R.drawable.ic_dialog_map, "Map", mapPendingIntent);

        /**
         * Wearable specific action
         * - visible only on the wearable
         * - phone notification not visible anymore as long as the WearableExtender is set
         */
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                android.R.drawable.ic_dialog_map, "Mapzz", mapPendingIntent).build();
        builder.extend(new NotificationCompat.WearableExtender().addAction(action));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(
                getApplicationContext());
        notificationManager.notify(MAP_INTENT_NOTIFICATION, builder.build());
    }

    public void sendBackgroundNotification(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle("BackgroundNotifications Sample")
                .setContentText("Time to learn about backgrounds!")
                .setSubText("Tap to view documentation about notifications.");

        /**
         * Notification background
         * - the large icon is used as the background image by default on a Wearable notification
         * - setBackground() can handle high resolution images
         * Recommendations:
         * - use 400x400 images for a static background
         * - use 640x400 images for parallax (left and right edges are used to simulate background
         * movement)
         * - save resources in the drawable-nodpi folder to avoid them being automatically resized
         * by the Android framework
         * - store resources in the drawable-hdpi folder when using the setContentIcon() method
         */
        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender()
                .setHintHideIcon(true)
                .setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.android));
        builder.extend(extender);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(
                getApplicationContext());
        notificationManager.notify(BACKGROUND_NOTIFICATION, builder.build());
    }

    public void sendExpandableNotification(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle("ExpandableNotification Sample")
                .setContentText("Time to learn about expandable notifications!")
                .setSubText("Tap to view documentation about notifications.");

        /**
         * Notification styles
         * - touch one of these notifications and it will expand to show more information
         * - on Android wear the content will automatically expand to fil the display, since there
         * is more vertical space available
         */
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText("Notification Big Text that should be long enough to extend on the " +
                "whole screen. So make this text longer by adding more text. Is this long enough? " +
                "Should I keep writing?");
        builder.setStyle(bigTextStyle);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(
                getApplicationContext());
        notificationManager.notify(EXPANDABLE_NOTIFICATION, builder.build());
    }
}
