package com.example.butler.tinynotes;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by Butler on 09/06/2015.
 */
public class Add_Notes extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotes);

        long date = System.currentTimeMillis();

        TextView Date = (TextView) findViewById(R.id.date);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy HH:mm ");
        String dateString = sdf.format(date);
        Date.setText(dateString);
        final EditText title = (EditText) findViewById(R.id.title);
       final EditText desc = (EditText) findViewById(R.id.desc);


        //---Button view---
        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Add a new student record
                ContentValues values = new ContentValues();

                values.put(DbCreate.TITLE,
                        ((EditText) findViewById(R.id.title)).getText().toString());

                values.put(DbCreate.DESC,
                        ((EditText) findViewById(R.id.desc)).getText().toString());

                values.put(DbCreate.DATE,
                        ((TextView) findViewById(R.id.date)).getText().toString());


                Uri uri = getContentResolver().insert(
                        DbCreate.CONTENT_URI, values);

                Toast.makeText(getBaseContext(),
                        "Note Added", Toast.LENGTH_LONG).show();
                title.setText(null);
                desc.setText(null);

            }
        });
    }
}