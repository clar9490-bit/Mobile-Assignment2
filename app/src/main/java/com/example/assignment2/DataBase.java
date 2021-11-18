package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


//create a super constructor for the class
public class DataBase extends SQLiteOpenHelper {

    public static final String LOCATION_TABLE = "LOCATION_TABLE";
    public static final String COLUMN_ADDRESS = "LOCATION_ADDRESS";
    public static final String COLUMN_LATITUDE = "LOCATION_LATITUDE";
    public static final String COLUMN_LONGITUDE = "LOCATION_LONGITUDE";
    public static final String COLUMN_ID = "LOCATION_TABLE";


    public DataBase(@Nullable Context context) {
        super(context, "address.db", null, 1);
    }


    //called on the first time a database is accessed. This is to create a new database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table ="CREATE TABLE " + LOCATION_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ADDRESS + " TEXT, " + COLUMN_LATITUDE + " FLOAT, " + COLUMN_LONGITUDE + " FLOAT)";

        sqLiteDatabase.execSQL(create_table);

        //Clear all entries
        String clear_table = "DELETE FROM " + LOCATION_TABLE;
        sqLiteDatabase.execSQL(clear_table);

        //create the initial 50 entries
        //Insert Statment did not work
       //String create_entries ="INSERT INTO " + LOCATION_TABLE +" ("+COLUMN_ID+" , "+COLUMN_ADDRESS+" , "+COLUMN_LATITUDE+" , "+COLUMN_LONGITUDE+") values (1, 'Quebec', 12, 12)";
       //sqLiteDatabase.execSQL(create_entries);
    }

    //this is called when the database version number changes. Prevents previous users apps from breaking when a design change occurs
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //trial for search
   Cursor search( String target){
            String query = "SELECT * FROM " + LOCATION_TABLE + " WHERE " + COLUMN_ADDRESS +" Like " + "'" +target + "%'";
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;
            if (db != null) {
                cursor = db.rawQuery(query, null);
            }
            return cursor;
    }

    public boolean addOne (DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        //take pairs of values and associate them
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ADDRESS, dataModel.getAddress());
        cv.put(COLUMN_LATITUDE, dataModel.getLatitude());
        cv.put(COLUMN_LONGITUDE, dataModel.getLongitude());

        long insert = db.insert(LOCATION_TABLE,null , cv);
        if (insert == -1){
            return  false;
        }
        else {
            return true;
        }
    }


    public boolean deleteOne(DataModel dataModel){
        //delete an entry if it exists in the database, else return false
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString ="DELETE FROM " + LOCATION_TABLE + " WHERE " + COLUMN_ID + " = " + dataModel.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }

    }

    public List<DataModel> getAll(){
        List<DataModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + LOCATION_TABLE;
        //read the table
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        //if true there were results
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String Address = cursor.getString(1);
                Float Latitude = cursor.getFloat(2);
                Float Longitude = cursor.getFloat(3);

                DataModel newData = new DataModel(id, Address, Latitude, Longitude);
                returnList.add(newData);
            } while (cursor.moveToNext());
        }
        else {
            //failed to get result do nothing
        }
        //close db connection
        cursor.close();
        db.close();
        return returnList;
    }
    public List<DataModel> getSearch(String target){

        System.out.println("Search was called");

        List<DataModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + LOCATION_TABLE + " WHERE " + COLUMN_ADDRESS +" LIKE " + "'%" +target + "%'";
        //read the table
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(queryString, null);
            System.out.println("Search was done");
            System.out.println(cursor.getCount());

            //if true there were results
            if(cursor.moveToFirst()) {
                do {

                    int id = cursor.getInt(0);
                    String Address = cursor.getString(1);
                    Float Latitude = cursor.getFloat(2);
                    Float Longitude = cursor.getFloat(3);

                    DataModel newData = new DataModel(id, Address, Latitude, Longitude);
                    returnList.add(newData);
                } while (cursor.moveToNext());
            }
            else {
                //failed do nothing
            }
            //close db connection
            cursor.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        db.close();
        return returnList;
    }

}
