package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Reference the elements on the page
    Button add_button, view_button, update_button;
    EditText text_name, num_lat, num_long;
    ListView results;
    ArrayAdapter myArrayAdapter;
    DataBase dataBase;
    ArrayList copyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the id of each reference
        add_button = findViewById(R.id.add_button);
        view_button = findViewById(R.id.view_button);
        update_button = findViewById(R.id.update_button);
        text_name = findViewById(R.id.text_name);

        num_lat = findViewById(R.id.num_lat);
        num_long = findViewById(R.id.num_long);
        results = findViewById(R.id.results);

        //show the database immediately
        dataBase =new DataBase (MainActivity.this);

        //automatically add new entries
        //just using 1 to represent the 50 entries
        try {
            addEntry("Toronto, Ontario, Canada","43.89", "-78.87");
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Cannot find Toronto", Toast.LENGTH_SHORT).show();
        }
        upDateResults(dataBase);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataModel dataModel;

                try {
                    dataModel = new DataModel(-1, text_name.getText().toString(), Float.parseFloat(num_lat.getText().toString()), Float.parseFloat(num_long.getText().toString()));

                    Toast.makeText(MainActivity.this, dataModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Please Fill in all the values correctly", Toast.LENGTH_SHORT).show();
                    dataModel = new DataModel(-1, "error", null, null);
                }

                DataBase dataBase = new DataBase(MainActivity.this);
                boolean success = dataBase.addOne(dataModel);
                Toast.makeText(MainActivity.this, "Success = "+ success, Toast.LENGTH_SHORT).show();

                upDateResults(dataBase);

            }
        });


        view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBase =new DataBase (MainActivity.this);
                //upDateResults(dataBase);
                searchResults(dataBase);


                    //attempt to use cursor for updating searching
                //Toast.makeText(MainActivity.this, "You clicked the update", Toast.LENGTH_SHORT).show();
                //Cursor cursor = (Cursor) dataBase.search(text_name.toString());

                //Cursor cursor;
                //try{
                //    cursor = dataBase.search(text_name.toString());
                //    Toast.makeText(MainActivity.this, "Search Worked", Toast.LENGTH_SHORT).show();
                //} catch (Exception e){
                //    Toast.makeText(MainActivity.this, "Search failed", Toast.LENGTH_SHORT).show();
                //}

            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBase =new DataBase (MainActivity.this);
                upDateResults(dataBase);
            }
        });

        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                DataModel clickedData = (DataModel) parent.getItemAtPosition(i);
                dataBase.deleteOne(clickedData);
                upDateResults(dataBase);
                Toast.makeText(MainActivity.this, "Deleted: "+ clickedData.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addEntry(String add_Add,String add_Lat, String add_Long ) throws IOException {
        DataModel dataModel;
        Geocoder gc = new Geocoder(this);
        if(gc.isPresent()) {
            List<Address> list = gc.getFromLocationName(add_Add, 1);
            Address address = list.get(0);
            String lat = String.valueOf(address.getLatitude());
            String lng = String.valueOf(address.getLongitude());
            try {
                dataModel = new DataModel(-1, add_Add, Float.parseFloat(lat), Float.parseFloat(lng));

                Toast.makeText(MainActivity.this, dataModel.toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Please Fill in all the values correctly", Toast.LENGTH_SHORT).show();
                dataModel = new DataModel(-1, "error", null, null);
            }
            DataBase dataBase = new DataBase(MainActivity.this);
            boolean success = dataBase.addOne(dataModel);
        } else {
            //incase GeoCode will not work
            try {
                dataModel = new DataModel(-1, add_Add, Float.parseFloat(add_Lat), Float.parseFloat(add_Long));

                Toast.makeText(MainActivity.this, dataModel.toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Please Fill in all the values correctly", Toast.LENGTH_SHORT).show();
                dataModel = new DataModel(-1, "error", null, null);
            }
            DataBase dataBase = new DataBase(MainActivity.this);
            boolean success = dataBase.addOne(dataModel);
        }
    }

    private void upDateResults(DataBase dataBase2) {
        myArrayAdapter = new ArrayAdapter<DataModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBase2.getAll());
        results.setAdapter(myArrayAdapter);


    }

    private void searchResults(DataBase dataBase3){
        String checkAddress = text_name.getText().toString();
        System.out.println(checkAddress);
        Toast.makeText(MainActivity.this, checkAddress, Toast.LENGTH_SHORT).show();
        myArrayAdapter = new ArrayAdapter<DataModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBase3.getSearch(checkAddress));
        results.setAdapter(myArrayAdapter);
    }





}