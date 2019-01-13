package com.example.dmitry.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class DelayedMessageService extends IntentService {

  public static final String EXTRA_MESSAGE = "message";
  public static final int NOTIFICATION_ID = 5453;
  private Handler handler;

  public DelayedMessageService() {
    super("DelayedMessageService");
  }

  @Override
  public int onStartCommand(@org.jetbrains.annotations.Nullable @Nullable Intent intent, int flags,
      int startId) {
    handler = new Handler();
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    synchronized (this) {
      try {
        wait(1000);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
      String text = intent.getStringExtra(EXTRA_MESSAGE);
      showText(text);
    }
  }

  private void showText(final String text) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
      }
    });
    Intent intent = new Intent(this, MainActivity.class);
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    stackBuilder.addParentStack(MainActivity.class);
    stackBuilder.addNextIntent(intent);
    PendingIntent pendingIntent =
        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT
        );
    Notification notification = new Notification.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(getString(R.string.app_name))
        .setAutoCancel(true)
        .setPriority(Notification.PRIORITY_MAX)
        .setDefaults(Notification.DEFAULT_VIBRATE)
        .setContentIntent(pendingIntent)
        .setContentText(text)
        .build();
    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(NOTIFICATION_ID, notification);

    Log.v("DelayedMessageService", "The message is: " + text);
  }
}
