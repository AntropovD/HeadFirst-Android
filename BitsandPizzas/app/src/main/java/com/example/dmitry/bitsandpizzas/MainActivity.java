package com.example.dmitry.bitsandpizzas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

  private static final String POSITION = "position";
  private static final String FRAGMENT_TAG = "visible_fragment";
  private ShareActionProvider shareActionProvider;
  private String[] titles;
  private ListView drawerList;
  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle drawerToggle;
  private int currentPosition = 0;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    titles = getResources().getStringArray(R.array.titles);
    drawerList = findViewById(R.id.drawer);
    drawerLayout = findViewById(R.id.drawer_layout);

    drawerList.setAdapter(new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_activated_1, titles
    ));

    drawerList.setOnItemClickListener(new DrawerItemClickListener());
    if (savedInstanceState != null) {
      currentPosition = savedInstanceState.getInt(POSITION);
      setActionBarTitle(currentPosition);
    } else {
      selectItem(0);
    }

    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
        R.string.open_drawer, R.string.close_drawer) {

      public void onDrawerClosed(View view) {
        super.onDrawerClosed(view);
        invalidateOptionsMenu();
      }

      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        invalidateOptionsMenu();
      }
    };
    drawerLayout.addDrawerListener(drawerToggle);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    getFragmentManager().addOnBackStackChangedListener(
        new FragmentManager.OnBackStackChangedListener() {
          public void onBackStackChanged() {
            FragmentManager fragMan = getFragmentManager();
            Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
            if (fragment instanceof TopFragment) {
              currentPosition = 0;
            }
            if (fragment instanceof PizzaMaterialFragment) {
              currentPosition = 1;
            }
            if (fragment instanceof PastaFragment) {
              currentPosition = 2;
            }
            if (fragment instanceof StoresFragment) {
              currentPosition = 3;
            }
            setActionBarTitle(currentPosition);
            drawerList.setItemChecked(currentPosition, true);
          }
        }
    );
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
    menu.findItem(R.id.action_share).setVisible(!drawerOpen);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    MenuItem menuItem = menu.findItem(R.id.action_share);
    shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
    setIntent("Example Text");

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
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }

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

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  private void selectItem(int position) {
    currentPosition = position;
    Fragment fragment;
    switch (position) {
      case 1:
        fragment = new PizzaMaterialFragment();
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
    ft.replace(R.id.content_frame, fragment, FRAGMENT_TAG);
    ft.addToBackStack(null);
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    ft.commit();

    setActionBarTitle(position);

    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
    drawerLayout.closeDrawer(drawerList);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(POSITION, currentPosition);
  }

  private void setActionBarTitle(int position) {
    String title;
    if (position == 0) {
      title = getResources().getString(R.string.app_name);
    } else {
      title = titles[position];
    }
    getSupportActionBar().setTitle(title);
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      selectItem(position);
    }
  }
}
