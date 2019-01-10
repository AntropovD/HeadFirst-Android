package com.example.dmitry.coffee;

import android.support.annotation.NonNull;

class Drink {

  static final Drink[] drinks = {
      new Drink("Latte", "A couple of espresso shots with steamed milk",
          R.drawable.latte),
      new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam",
          R.drawable.cappuccino),
      new Drink("Filter", "Highest quality beans roasted and brewed fresh",
          R.drawable.filter)
  };
  private final String name;
  private final String description;
  private final int imageResourceId;

  private Drink(String name, String description, int imageResourceId) {
    this.name = name;
    this.description = description;
    this.imageResourceId = imageResourceId;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  int getImageResourceId() {
    return imageResourceId;
  }

  @NonNull
  public String toString() {
    return this.name;
  }
}
