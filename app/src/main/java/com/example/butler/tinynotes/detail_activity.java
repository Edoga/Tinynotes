package com.example.butler.tinynotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.butler.tinynotes.DbCreate.DatabaseHelper;



public class detail_activity extends ActionBarActivity {
SQLiteDatabase db;
    String mForecastStr;
    DatabaseHelper databaseHelper;
    String[] compare={mForecastStr};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

         databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();


            mForecastStr = intent.getStringExtra("obi");
            String comment =  intent.getStringExtra("comment");
            String date2 = intent.getStringExtra("dates");
            ((TextView) findViewById(R.id.odin))
                    .setText(mForecastStr);
            ((TextView) findViewById(R.id.thor))
                    .setText(comment);
            ((TextView) findViewById(R.id.loki))
                    .setText(date2);

    }
    public void delete1(View view){
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_activity, menu);
        return true;
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
