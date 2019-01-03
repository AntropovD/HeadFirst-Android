package com.example.dmitry.workout;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
    implements WorkoutListFragment.WorkoutListListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public void itemClicked(long id) {
    WorkoutDetailFragment fragment = new WorkoutDetailFragment();
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    fragment.setWorkout(id);
    transaction.replace(R.id.fragment_container, fragment);
    transaction.addToBackStack(null);
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    transaction.commit();
  }
}
