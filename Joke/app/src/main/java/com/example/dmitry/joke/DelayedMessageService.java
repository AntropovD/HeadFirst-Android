package com.example.dmitry.joke;

import android.app.IntentService;
import android.content.Intent;

public class DelayedMessageService extends IntentService {


  public DelayedMessageService() {
    super("DelayedMessageService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if (intent != null) {

    }
  }
}
