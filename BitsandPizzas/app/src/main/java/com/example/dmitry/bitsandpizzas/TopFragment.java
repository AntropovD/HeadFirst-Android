package com.example.dmitry.bitsandpizzas;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class TopFragment extends Fragment {

  public TopFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    RelativeLayout layout = (RelativeLayout)
        inflater.inflate(R.layout.fragment_top, container, false);

    RecyclerView pizzaRecycler = (RecyclerView) layout.findViewById(R.id.pizza_recycler);

    String[] pizzaNames = new String[Pizza.pizzas.length];
    for (int i = 0; i < pizzaNames.length; i++) {
      pizzaNames[i] = Pizza.pizzas[i].getName();
    }
    int[] pizzaImages = new int[Pizza.pizzas.length];
    for (int i = 0; i < pizzaImages.length; i++) {
      pizzaImages[i] = Pizza.pizzas[i].getImageResourceId();
    }

    CaptionedImagesAdapter adapter =
        new CaptionedImagesAdapter(pizzaNames, pizzaImages);

    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
    pizzaRecycler.setLayoutManager(layoutManager);

    pizzaRecycler.setAdapter(adapter);
    adapter.setListener(new CaptionedImagesAdapter.Listener() {
      public void onClick(int position) {
        Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
        intent.putExtra(PizzaDetailActivity.EXTRA_PIZZANO, position);
        getActivity().startActivity(intent);
      }
    });

    return layout;
  }
}
