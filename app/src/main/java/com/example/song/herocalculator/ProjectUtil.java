package com.example.song.herocalculator;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class type {
    public static final String[] BUFFTYPE = {"AD", "AP", "ADD", "APD", "MANA", "BLOOD", "price"};
    public static final int BUFFTYPENUM = 7;
    public static final int HERO = 0;
    public static final int ITEM = 1;
}

class Buff {
    float[] buff;

    public Buff() {
        buff = new float[type.BUFFTYPENUM];
        for (int i = 0; i < type.BUFFTYPENUM; i++) {
            buff[i] = 0;
        }
    }

    public void add(Buff otherBuff) {
        for (int i = 0; i < type.BUFFTYPENUM; i++) {
            buff[i] += otherBuff.buff[i];
        }
    }
}

class ItemFactory {
    private SQLiteDatabase db;
    private Item item;

    public ItemFactory(Context activity) {
        DataBaseHelper dbHelper = new DataBaseHelper(activity);
        try {

            dbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            dbHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }
        db = dbHelper.getReadableDatabase();
        item = new Item();
    }


    public Item getItemByKeyword(String keyword) {//new String[]{"name", "id"}
        Cursor cursor = db.query("item", null, "name=? or _id=?", new String[]{keyword, keyword}, null, null, null);
        if (cursor.moveToFirst()) {
            item.id = cursor.getInt(cursor.getColumnIndex("_id"));
            item.picturePath = "p"+Integer.toString(item.id);
            item.name = cursor.getString(cursor.getColumnIndex("name"));
            item.description = cursor.getString(cursor.getColumnIndex("description"));
            if (cursor.getInt(cursor.getColumnIndex("isHero")) == 1) {
                item.itemType = type.HERO;
            } else {
                item.itemType = type.ITEM;
            }
            for (int i = 0; i < type.BUFFTYPENUM; i++) {
                item.buff.buff[i] = cursor.getFloat(cursor.getColumnIndex(type.BUFFTYPE[i]));
            }
            cursor.close();
        } else {
            Log.w("NOTICE", "查无此项");
        }
        return item;
    }
}

class Item {
    int id;
    public int itemType;
    public Buff buff;
    public String name;
    public String picturePath;
    public String description;

    public Item() {
        id = -1;
        itemType = type.HERO;
        buff = new Buff();
        name = "NULL";
        picturePath = "0";
        description = "NULL";
    }

    public String getName() {
        return name;
    }


    public int getItemType() {
        return itemType;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public String getDescription() {
        return description;
    }


    private String printBuff() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < type.BUFFTYPENUM; i++) {
            result.append(type.BUFFTYPE[i]).append(": ").append(Float.toString(buff.buff[i])).append("\n");
        }
        return result.toString();
    }

    public String print() {
        return "名称: " + name + "\n描述: \n" + description + "\n" + printBuff();
    }

    public void add(Item item) {
        buff.add(item.buff);
    }
}







class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.song.herocalculator/databases/";

    private static String DB_NAME = "data.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}