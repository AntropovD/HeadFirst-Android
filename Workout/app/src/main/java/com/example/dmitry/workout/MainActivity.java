package com.example.dmitry.workout;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity
    implements WorkoutListFragment.WorkoutListListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public void itemClicked(long id) {
    View fragmentContainer = findViewById(R.id.fragment_container);
    if (fragmentContainer != null) {
      WorkoutDetailFragment fragment = new WorkoutDetailFragment();
      FragmentTransaction transaction = getFragmentManager().beginTransaction();
      fragment.setWorkout(id);
      transaction.replace(R.id.fragment_container, fragment);
      transaction.addToBackStack(null);
      transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      transaction.commit();
    } else {
      Intent intent = new Intent(this, DetailActivity.class);
      intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, (int) id);
      startActivity(intent);
    }
  }
}
