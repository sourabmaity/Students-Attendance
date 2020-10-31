package com.example.studentattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class StuPreActivity extends AppCompatActivity {
    ListView listView;
    ArrayList key,values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_pre);
        Bundle depart=getIntent().getExtras();
        key=depart.getStringArrayList("KEY");
        values=depart.getStringArrayList("VALUES");
        //Log.d("WHO", key+" String "+values );
        listView=(ListView)findViewById(R.id.stu_details);
        ArrayList<String> combineList=new ArrayList<>();
        for (int i=0;i<key.size();i++)
        {
            combineList.add(key.get(i)+" : "+values.get(i));
            //Log.d("WHO", key.get(i)+" : "+values.get(i) );
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,combineList);
        listView.setAdapter(adapter);
    }
}