package com.example.studentattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class PresentActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String dep,semi;
    int totalPresent=0,flag=1;
    Map<String, Object> k;
    Map<Integer,String> slide_roll_name=new TreeMap<>();
    Map<String, Object> present = new TreeMap<>();
    ListView lstView;
    ArrayList keyList,valueList;
    TextView tp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present);
        Bundle depart=getIntent().getExtras();
        dep=depart.getString("DEPART");
        semi=depart.getString("SEM");
        lstView=(ListView)findViewById(R.id.listv);
        tp=(TextView)findViewById(R.id.totalPresent);

        data_read();
        Toast.makeText(PresentActivity.this,"Department :"+dep+" Sem: "+semi,Toast.LENGTH_SHORT).show();
        //save=(Button)findViewById(R.id.saveData);
        //close=(Button)findViewById(R.id.dontSave);
        /*save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PresentActivity.this, "Save ", Toast.LENGTH_LONG).show();
            }
        });
        /*close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PresentActivity.this, "Close ", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    void data_read()
    {
        DocumentReference docRef = db.collection("Department/"+dep+"/"+semi).document("Students");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("WHO", "DocumentSnapshot data: " + document.getData());
                        k = document.getData();
                        for (Map.Entry<String, Object> mapElement : k.entrySet()) {
                            int ke =Integer.parseInt(mapElement.getKey());
                            //Log.d("WHO", "Key" + mapElement.getKey());
                            Map<String, String> value = (Map<String, String>) mapElement.getValue();
                            //List roll_numbers= Arrays.asList(value);
                            for (Map.Entry<String, String> map : value.entrySet()) {
                                String s1 = map.getKey();
                                if (s1.equals("Name")) {
                                    slide_roll_name.put(ke, map.getValue());
                                }
                            }
                        }
                    } else {
                        Log.d("WHO", "No such document");
                        tp.setText("No document");
                        Toast.makeText(PresentActivity.this,"No document",Toast.LENGTH_SHORT).show();
                        flag=0;
                        onBackPressed();
                    }
                } else {
                    Log.d("WHO", "get failed with ", task.getException());
                }
                //stu_roll_name=new  TreeMap<Integer,String> (slide_roll_name);

                keyList = new ArrayList(slide_roll_name.keySet());
                valueList=new ArrayList(slide_roll_name.values());
                LstViewAdapter adapter=new LstViewAdapter(PresentActivity.this,R.layout.list_item,R.id.name2,valueList,keyList);
                lstView.setAdapter(adapter);
            }
        });
    }

    public void clickMe(View v){
        TextView textView=v.findViewById(R.id.roll_no2);
        //Toast.makeText(this, "Button "+textView.getText(), Toast.LENGTH_LONG).show();
        Map<String, String> data_of_stu = (Map<String, String>) k.get(textView.getText());
        ArrayList send_keyList = new ArrayList(data_of_stu.keySet());
        ArrayList send_valueList=new ArrayList(data_of_stu.values());
        //Log.d("WHO", send_keyList+"Values data: " + send_valueList);
        Intent send=new Intent(PresentActivity.this,StuPreActivity.class);
        send.putExtra("KEY",send_keyList);
        send.putExtra("VALUES",send_valueList);
        startActivity(send);
        //Log.d("WHO", "p1: "+p1);
    }

    public void checkStu(View v){
        Button bt=v.findViewById(R.id.bt);
        int position=Integer.parseInt((String)bt.getContentDescription());
        //TextView textView=(TextView)v.findViewById(R.id.roll_no2);
        if(bt.getText().equals("Yes")){
            //Toast.makeText(this, "Yes "+keyList.get(position), Toast.LENGTH_LONG).show();
            present.put(keyList.get(position).toString(),valueList.get(position));
            totalPresent++;
        }
        else if(bt.getText().equals("No")){
            //Toast.makeText(this, "No "+keyList.get(position), Toast.LENGTH_LONG).show();
            present.remove(keyList.get(position).toString());
            totalPresent--;
        }
        tp.setText("Total Present : "+totalPresent);
    }
    public void saveIt(View v){
        Toast.makeText(PresentActivity.this, "Data save successfully", Toast.LENGTH_LONG).show();
        Log.d("WHO", "p1: "+present);
        flag=0;
        onBackPressed();
    }
    public void notSave(View v){
        Toast.makeText(PresentActivity.this, "Data is not save ", Toast.LENGTH_LONG).show();
        flag=1;
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(flag==0){
            super.onBackPressed();
        }
        else{
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(PresentActivity.this);
            builder.setMessage("Do you want to exit ?");
            builder.setTitle("Alert !");
            builder.setCancelable(false);
            builder
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {
                                    flag=0;
                                    onBackPressed();
                                }
                            });
            builder
                    .setNegativeButton(
                            "No",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}