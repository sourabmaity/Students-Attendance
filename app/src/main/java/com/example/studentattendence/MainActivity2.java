package com.example.studentattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity2 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> k;
    Map<Integer,String> slide_roll_name=new TreeMap<>();
    ListView rolls;
    String dep,semi;
    ArrayList keyList,valueList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle depart=getIntent().getExtras();
        dep=depart.getString("DEPART");
        semi=depart.getString("SEM");
        data_read();
        Toast.makeText(MainActivity2.this,"Department :"+dep+" Sem: "+semi,Toast.LENGTH_SHORT).show();

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
                        //tp.setText("No document");
                        Toast.makeText(MainActivity2.this,"No document",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                } else {
                    Log.d("WHO", "get failed with ", task.getException());
                }
                //stu_roll_name=new  TreeMap<Integer,String> (slide_roll_name);

                keyList = new ArrayList(slide_roll_name.keySet());
                valueList=new ArrayList(slide_roll_name.values());
                rolls=(ListView)findViewById(R.id.rollList);

                ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity2.this,android.R.layout.simple_list_item_1,keyList);
                rolls.setAdapter(arrayAdapter);
                rolls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Map<String, String> data_of_stu = (Map<String, String>) k.get(keyList.get(position).toString());
                        Log.d("WHO", "Values data: " + keyList.get(position)+data_of_stu);
                        ArrayList send_keyList = new ArrayList(data_of_stu.keySet());
                        ArrayList send_valueList=new ArrayList(data_of_stu.values());
                        //Log.d("WHO", send_keyList+"Values data: " + send_valueList);
                        Intent send=new Intent(MainActivity2.this,StuPreActivity.class);
                        send.putExtra("KEY",send_keyList);
                        send.putExtra("VALUES",send_valueList);
                        startActivity(send);
                    }
                });

            }
        });
    }
    /*semList=(ListView)findViewById(R.id.tray);
        final ArrayList<String> stupreList=new ArrayList<>();
        stupreList.add("Present");
        stupreList.add("Students");
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,stupreList);
        semList.setAdapter(arrayAdapter);*/
        /*logout=findViewById(R.id.sign_out);
        name=findViewById(R.id.mail_name);
        id=findViewById(R.id.mail_id);
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount!=null){
            name.setText(signInAccount.getDisplayName());
            id.setText(signInAccount.getEmail());
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent sem=new Intent(MainActivity2.this,MainActivity.class);
                startActivity(sem);
            }
        });
        context=this;
        lstView=(ListView)findViewById(R.id.listv);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d("WHO", " => ");
                Toast.makeText(context, "An item of the ListView is clicked.", Toast.LENGTH_SHORT).show();
            }
        });
        Map<Integer,String> items=new HashMap<Integer, String>();
        items.put(1,"a");
        items.put(2,"b");
        items.put(3,"c");
        items.put(4,"d");
        items.put(5,"e");
        items.put(6,"f");
        ArrayList keyList = new ArrayList(items.keySet());
        ArrayList valueList=new ArrayList(items.values());
        //String[] items={"1","2","3","4","5"};
        //String[] items2={"a","b","c","d","e","f"};
        LstViewAdapter adapter=new LstViewAdapter(this,R.layout.list_item,R.id.name2,valueList,keyList);
        // Bind data to the ListView
        lstView.setAdapter(adapter);
        //ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,keyList);
        //lstview.setAdapter(arrayAdapter);



    }

    public void clickMe2(View v){
        //Button bt=(Button)view;
        TextView textView=v.findViewById(R.id.roll_no2);
        Toast.makeText(this, "Button "+textView.getText(), Toast.LENGTH_LONG).show();
    }*/


}
