package com.example.dmitry.bitsandpizzas;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

  private ShareActionProvider shareActionProvider;
  private String[] titles;
  private ListView drawerList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    MenuItem menuItem = menu.findItem(R.id.action_share);
    shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
    setIntent("Example Text");

    titles = getResources().getStringArray(R.array.titles);
    drawerList = findViewById(R.id.drawer);
    drawerList.setAdapter(new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_activated_1, titles
    ));

    drawerList.setOnItemClickListener(new DrawerItemClickListener());
    return super.onCreateOptionsMenu(menu);
  }

  private void setIntent(String text) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, text);
    shareActionProvider.setShareIntent(intent);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_create_order:
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        return true;
      case R.id.action_settings:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      selectItem(position);
    }

    private void selectItem(int position) {
      Fragment fragment;
      switch(position) {
        case 1:
          fragment = new PizzaFragment();
          break;
        case 2:
          fragment = new PastaFragment();
          break;
        case 3:
          fragment = new StoresFragment();
          break;
        default:
          fragment = new TopFragment();
      }
      FragmentTransaction ft = getFragmentManager().beginTransaction();
      ft.replace(R.id.content_frame, fragment);
      ft.addToBackStack(null);
      ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      ft.commit();
    }
  }
}
