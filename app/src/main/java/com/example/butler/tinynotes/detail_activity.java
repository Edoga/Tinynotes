package com.example.butler.tinynotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.butler.tinynotes.DbCreate.DatabaseHelper;



public class detail_activity extends ActionBarActivity {
SQLiteDatabase db;
    String comment;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

         databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();


           String mForecastStr = intent.getStringExtra("obi");
            comment =  intent.getStringExtra("comment");
            String date2 = intent.getStringExtra("dates");
            ((TextView) findViewById(R.id.odin))
                    .setText(mForecastStr);
            ((TextView) findViewById(R.id.thor))
                    .setText(comment);
            ((TextView) findViewById(R.id.loki))
                    .setText(date2);


    }
    public void delete1(View view){

        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        long note_id = getIntent().getLongExtra("note_id",-1);
                        String id = String.valueOf(note_id);
                        SQLiteDatabase db = databaseHelper.getWritableDatabase();
                        // db.rawQuery("DELETE FROM notes WHERE _id = ?",id);
                        String query = "DELETE FROM notes WHERE _id = " + id+ "";
                        db.execSQL(query);
                        db.close();
                        Intent intent = new Intent(detail_activity.this, MainTabControllerActivity.class);
                        startActivity(intent);
                        //String URL = "content://com.example.butler.tinynotes/notes";

                        //Uri courses = Uri.parse(URL);
                        //int count = getContentResolver().delete(courses,DbCreate.TITLE,compare);

                        Toast.makeText(getBaseContext(),"Deleted Successfully",Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailfragment, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
      ShareActionProvider mShareActionProvider =
              (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }

        // Return true to display menu
        return true;
    }
    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                comment);
        return shareIntent;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
