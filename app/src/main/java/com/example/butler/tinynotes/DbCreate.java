package com.example.butler.tinynotes;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Butler on 10/06/2015.
 */
public class DbCreate extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.butler.tinynotes";
    static final String URL = "content://" + PROVIDER_NAME + "/notes";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String _ID = "_id";
    static final String TITLE = "note_title";
    static final String DESC = "note_desc";
    static final String DATE = "date";

    private static HashMap<String, String> NOTES_PROJECTION_MAP;

    static final int NOTES = 1;
    static final int NOTES_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "notes", NOTES);
        uriMatcher.addURI(PROVIDER_NAME, "notes/#", NOTES_ID);
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "tiny_notes";
    static final String NOTE_TABLE_NAME = "notes";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + NOTE_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " note_title TEXT NOT NULL, " +
                    " note_desc VARCHAR(255)," +
                    " date TEXT NOT NULL);";

    public static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  NOTE_TABLE_NAME);
            onCreate(db);
        }
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(NOTE_TABLE_NAME);


        switch (uriMatcher.match(uri)) {
            case NOTES:
                qb.setProjectionMap(NOTES_PROJECTION_MAP);
                break;

            case NOTES_ID:
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on student names
             */
            sortOrder = TITLE;
        }
        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;

    }


    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all student records
             */
            case NOTES:
                return "vnd.android.cursor.dir/vnd.example.courses";

            /**
             * Get a particular student
             */
            case NOTES_ID:
                return "vnd.android.cursor.item/vnd.example.butler.courses";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long timetable = db.insert(	NOTE_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */

        if (timetable > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, timetable);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case NOTES:
                count = db.delete(NOTE_TABLE_NAME, selection, selectionArgs);
                break;

            case NOTES_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( NOTE_TABLE_NAME, _ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }}



