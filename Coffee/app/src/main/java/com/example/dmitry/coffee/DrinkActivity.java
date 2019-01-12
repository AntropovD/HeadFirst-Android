package com.example.dmitry.coffee;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

  public static final String EXTRA_DRINKNO = "drinkNo";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drink);

    int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);

    try {
      SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
      SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
      Cursor cursor = db.query("DRINK",
          new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"}, "_id = ?",
          new String[]{Integer.toString(drinkNo)}, null, null, null
      );
      if (cursor.moveToFirst()) {
        String nameText = cursor.getString(0);
        String descriptionText = cursor.getString(1);
        int resourceId = cursor.getInt(2);
        boolean isFavorite = ((cursor.getInt(3)) == 1);

        ImageView photo = findViewById(R.id.photo);
        photo.setImageResource(resourceId);
        photo.setContentDescription(nameText);

        TextView name = findViewById(R.id.name);
        name.setText(nameText);

        TextView description = findViewById(R.id.description);
        description.setText(descriptionText);

        CheckBox favorite = findViewById(R.id.favorite);
        favorite.setChecked(isFavorite);
      }
    } catch (Exception e) {
      Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
      toast.show();
    }
  }

  public void onFavoriteClicked(View view) {
    int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
    new UpdateDrinkTask().execute(drinkNo);
  }

  private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {

    ContentValues drinkValues;

    protected void onPreExecute() {
      CheckBox favorite = findViewById(R.id.favorite);
      drinkValues = new ContentValues();
      drinkValues.put("FAVORITE", favorite.isChecked());
    }

    protected Boolean doInBackground(Integer... drinks) {
      int drinkNo = drinks[0];
      SQLiteOpenHelper starbuzzDatabaseHelper =
          new StarbuzzDatabaseHelper(DrinkActivity.this);
      try {
        SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
        db.update("DRINK", drinkValues,
            "_id = ?", new String[]{Integer.toString(drinkNo)});
        db.close();
        return true;
      } catch (SQLiteException e) {
        return false;
      }
    }

    protected void onPostExecute(Boolean success) {
      if (!success) {
        Toast toast = Toast.makeText(DrinkActivity.this,
            "Database unavailable", Toast.LENGTH_SHORT);
        toast.show();
      }
    }
  }
}
