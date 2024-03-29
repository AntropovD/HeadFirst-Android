package com.example.dmitry.bitsandpizzas;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class PizzaFragment extends ListFragment {

  public PizzaFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    ArrayAdapter<String> adapter = new ArrayAdapter<>(
        inflater.getContext(),
        android.R.layout.simple_list_item_1,
        getResources().getStringArray(R.array.pizzas)
    );

    setListAdapter(adapter);
    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
