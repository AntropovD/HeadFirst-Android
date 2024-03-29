package com.example.dmitry.workout;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment implements View.OnClickListener{

  private int seconds = 0;
  private boolean isRunning = false;
  private boolean wasRunning;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
    runTimer(layout);

    Button startButton = layout.findViewById(R.id.start_button);
    startButton.setOnClickListener(this);
    Button stopButton = layout.findViewById(R.id.stop_button);
    stopButton.setOnClickListener(this);
    Button resetButton = layout.findViewById(R.id.reset_button);
    resetButton.setOnClickListener(this);

    return layout;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState != null) {
      seconds = savedInstanceState.getInt("seconds");
      isRunning = savedInstanceState.getBoolean("isRunning");
      wasRunning = savedInstanceState.getBoolean("wasRunning");
    }
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    savedInstanceState.putInt("seconds", seconds);
    savedInstanceState.putBoolean("isRunning", isRunning);
    super.onSaveInstanceState(savedInstanceState);
  }

  @Override
  public void onPause() {
    super.onPause();
    wasRunning = isRunning;
    isRunning = false;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (wasRunning) {
      isRunning = true;
    }
  }

  public void onClickStart(View view) {

    isRunning = true;
  }

  public void onClickStop(View view) {

    isRunning = false;
  }

  public void onClickReset(View view) {
    isRunning = false;
    seconds = 0;
  }

  private void runTimer(View view) {
    final TextView timeView = view.findViewById(R.id.time_view);
    final Handler handler = new Handler();

    handler.post(new Runnable() {
      @Override
      public void run() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String format = "%d:%02d:%02d";
        String time = String.format(format, hours, minutes, secs);
        timeView.setText(time);
        if (isRunning) {
          seconds++;
        }
        handler.postDelayed(this, 100);
      }
    });
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.start_button:
        onClickStart(v);
        break;
      case R.id.stop_button:
        onClickStop(v);
        break;
      case R.id.reset_button:
        onClickReset(v);
        break;
    }
  }
}
