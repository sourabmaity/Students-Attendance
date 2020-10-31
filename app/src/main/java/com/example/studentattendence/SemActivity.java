package com.example.studentattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.HashBiMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.*;

public class SemActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListView semList;
    int flag=0;
    String dep,sem;
    final ArrayList<String> departmentList=new ArrayList<>();
    final ArrayList<String> semArrayList=new ArrayList<>();
    final ArrayList<String> stupreList=new ArrayList<>();
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(myToolbar);
        ActionBar ab=getSupportActionBar();
        //getSupportActionBar().setTitle("Stu");
        ab.setDisplayHomeAsUpEnabled(true);
        mAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //Bundle depart=getIntent().getExtras();
        final Map<String,String> slide_roll_name=new LinkedHashMap<String, String>();//Map<String, String>(Collections.reverseOrder());
        //flag=depart.getInt("FLAG");
        semList=(ListView)findViewById(R.id.semlistView);
        //departmentList=new ArrayList<>();
        departmentList.add("CE");
        departmentList.add("ME");
        departmentList.add("EE");
        departmentList.add("ETCE");
        departmentList.add("CST");
        departmentList.add("AE");
        //final ArrayList<String> semArrayList=new ArrayList<>();
        semArrayList.add("1st Sem");
        semArrayList.add("2nd Sem");
        semArrayList.add("3rd Sem");
        semArrayList.add("4th Sem");
        semArrayList.add("5th Sem");
        semArrayList.add("6th Sem");
        //final ArrayList<String> stupreList=new ArrayList<>();
        stupreList.add("Present");
        stupreList.add("Students");
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,departmentList);
        semList.setAdapter(arrayAdapter);
        semList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(flag==0){
                    ArrayAdapter arrayAdapter=new ArrayAdapter(SemActivity.this,android.R.layout.simple_list_item_1,semArrayList);
                    dep=departmentList.get(position);
                    semList.setAdapter(arrayAdapter);
                    flag++;
                }
                else if(flag==1){
                    ArrayAdapter arrayAdapter=new ArrayAdapter(SemActivity.this,android.R.layout.simple_list_item_1,stupreList);
                    sem=semArrayList.get(position);
                    semList.setAdapter(arrayAdapter);
                    flag++;
                }
                else{
                    if(position==0){
                        Intent send=new Intent(SemActivity.this,PresentActivity.class);
                        send.putExtra("DEPART",dep);
                        send.putExtra("SEM",sem);
                        startActivity(send);
                    }
                    else {
                        Intent send=new Intent(SemActivity.this,MainActivity2.class);
                        send.putExtra("DEPART",dep);
                        send.putExtra("SEM",sem);
                        startActivity(send);
                    }

                }


                //DocumentReference docRef = db.collection("Department").document("AE");
                /*db.collection("Department")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("WHO", document.getId() + " => " + document.getData());
                                        //Log.d("WHO", " => " + document.getReference());
                                        Toast.makeText(SemActivity.this,"data : "+document.getData(),Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.w("WHO", "Error getting documents.", task.getException());
                                }
                            }
                        });*/
                /*db.collection("Department/AE/1st Sem")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("WHO", document.getId() + " => " + document.getData());
                                        //Log.d("WHO", " => " + document.getReference());
                                        //Toast.makeText(SemActivity.this,"data : "+document.getData(),Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.w("WHO", "Error getting documents.", task.getException());
                                }
                            }
                        });*/


                /*DocumentReference docRef = db.collection("Department/AE/1st Sem").document("Students");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("WHO", "DocumentSnapshot data: " + document.getData());
                                Map<String, Object> k = document.getData();
                                for (Map.Entry<String, Object> mapElement : k.entrySet()) {
                                    String key=mapElement.getKey();
                                    Log.d("WHO", "Key"+mapElement.getKey());
                                    Map<String,String> value = (Map<String, String>) mapElement.getValue();
                                    //List roll_numbers= Arrays.asList(value);
                                    for(Map.Entry<String, String> map : value.entrySet())
                                    {
                                        String s1=map.getKey();
                                        if(s1.equals("Name"))
                                        {
                                            slide_roll_name.put(key,map.getValue());
                                            //Log.d("WHO", "String"+map.getKey()+" Value"+map.getValue());
                                        }

                                    }

                                }
                            } else {
                                Log.d("WHO", "No such document");
                            }
                        } else {
                            Log.d("WHO", "get failed with ", task.getException());
                        }
                        ArrayList keyList = new ArrayList(slide_roll_name.keySet());
                        for (int i = keyList.size() - 1; i >= 0; i--) {
                            //get key
                            String key = (String)keyList.get(i);
                            //get value corresponding to key
                            String value = slide_roll_name.get(key);
                            Log.d("WHO", "String"+key+" Value"+value);

                        }
                        /*for(Map.Entry<String, String> map : slide_roll_name.entrySet())
                        {
                            Log.d("WHO", "String"+map.getKey()+" Value"+map.getValue());
                        }
                    }
                });*/





            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.sign_out,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.signOut:
                mAuth.signOut();

                // Google sign out
                mGoogleSignInClient.signOut().addOnCompleteListener(this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent sem=new Intent(SemActivity.this,MainActivity.class);
                                startActivity(sem);
                            }
                        });
                //FirebaseAuth.getInstance().signOut();

                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }

        //Log.w("WHO", ""+item.getItemId()+" "+android.R.id.home);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(flag==2){
            ArrayAdapter arrayAdapter=new ArrayAdapter(SemActivity.this,android.R.layout.simple_list_item_1,semArrayList);
            semList.setAdapter(arrayAdapter);
            flag--;
        }
        else if(flag==1){
            ArrayAdapter arrayAdapter=new ArrayAdapter(SemActivity.this,android.R.layout.simple_list_item_1,departmentList);
            semList.setAdapter(arrayAdapter);
            flag--;
        }
        else if(flag==0){
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }
}