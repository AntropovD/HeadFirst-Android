package com.example.dmitry.joke;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class DelayedMessageService extends IntentService {

  private Handler handler;
  public static final String EXTRA_MESSAGE = "message";

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
      try{
        wait(10000);
      }
      catch ( InterruptedException ex) {
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
    Log.v("DelayedMessageService", "The message is: " + text);
  }
}
