package com.example.butler.tinynotes;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import com.example.butler.tinynotes.DbCreate;

/**
 * Created by Butler on 09/06/2015.
 */
public class View_Notes extends ListActivity {
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        // Retrieve student records
        String URL = "content://com.example.butler.tinynotes/notes";

        Uri courses = Uri.parse(URL);
        Cursor c = managedQuery(courses, null, null, null, "note_title");



        // the desired columns to be bound
        String[] columns = new String[] {DbCreate.TITLE, DbCreate.DATE};
        // the XML defined views which the data will be bound to
        int[] to = new int[] { R.id.title1, R.id.date1 };

        // create the adapter using the cursor pointing to the desired data as well as the layout information
        final SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.activity_viewnotes, c, columns, to);

        // set this adapter as your ListActivity's adapter
        this.setListAdapter(mAdapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                String obi = c.getString(c.getColumnIndex("note_title"));
                long note_id = c.getLong(c.getColumnIndex(DbCreate._ID));
                String rony = c.getString(c.getColumnIndex("note_desc"));
                String chima = c.getString(c.getColumnIndex("date"));

                // Toast.makeText(getBaseContext(), "hello" + mAdapter.getItemId(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(View_Notes.this, detail_activity.class);
                intent.putExtra("note_id",note_id);
                intent.putExtra("obi", obi);
                intent.putExtra("comment", rony);
                intent.putExtra("dates",chima);




                startActivity(intent);



            }
        });
    }
}
