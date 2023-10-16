package com.example.sqlitefirstproject;



import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class view extends AppCompatActivity {


    SearchView searchView;
    ListView lst1;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        SQLiteDatabase db = openOrCreateDatabase("SliteDb",Context.MODE_PRIVATE,null);

        lst1 = findViewById(R.id.lst1);
        searchView = findViewById(R.id.searchView);
        final Cursor c = db.rawQuery("select * from records",null);
        int id = c.getColumnIndex("id");
        int name = c.getColumnIndex("name");
        int course = c.getColumnIndex("course");
        int fee = c.getColumnIndex("fee");
        titles.clear();


     /*   arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,titles);
        lst1.setAdapter(arrayAdapter);*/

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        lst1.setAdapter(arrayAdapter);

         /*For Sqlite data Seraching help in list*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });


        final  ArrayList<student> stud = new ArrayList<student>();


        if(c.moveToFirst())
        {
            do{
                student stu = new student();
                stu.id = c.getString(id);
                stu.name = c.getString(name);
                stu.course = c.getString(course);
                stu.fee = c.getString(fee);
//                stu.titles=c.toString();
                stud.add(stu);


                titles.add(c.getString(id) + " \t " + c.getString(name) + " \t "  + c.getString(course) + " \t "  + c.getString(fee) );

            } while(c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
            lst1.invalidateViews();



        }

        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String aa = titles.get(position).toString();
                student stu = stud.get(position);
                Intent i = new Intent(getApplicationContext(),edit.class);
                i.putExtra("id",stu.id);
                i.putExtra("name",stu.name);
                i.putExtra("course",stu.course);
                i.putExtra("fee",stu.fee);
//                i.putExtra("titles",stu.titles);
                startActivity(i);

            }
        });

    }
}
