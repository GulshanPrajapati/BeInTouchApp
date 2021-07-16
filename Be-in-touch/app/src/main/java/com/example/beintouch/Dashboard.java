package com.example.beintouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Dashboard extends AppCompatActivity {
    private Button btn;
    public TextView firstname,lastname;
    public LinearLayout profile , text2015, text2016, text2017, text2018, feed, post, info;
    private FirebaseAuth mAuth;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //set firstname and lastname
        firstname = findViewById(R.id.textfirstname);
        lastname = findViewById(R.id.textlastname);
        profile = findViewById(R.id.userProfile);
        text2015 = findViewById(R.id.collegetext15);
        text2016 = findViewById(R.id.collegetext16);
        text2017 = findViewById(R.id.collegetext17);
        text2018 = findViewById(R.id.collegetext18);
        feed = findViewById(R.id.feedback);
        post = findViewById(R.id.armietpost);
        info = findViewById(R.id.info);



        //userProfile on click listner

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),EditProfile.class);
                startActivity(i);
            }
        });


            //activitys
        text2015.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(),students2015.class);
                startActivity(i);
            }
        });

        text2016.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(),students2016.class);
                startActivity(i);
            }
        });

        text2017.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(),students2017.class);
                startActivity(i);
            }
        });

        text2018.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(),students2018.class);
                startActivity(i);
            }
        });

        //feedback
        feed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(),feedback.class);
                startActivity(i);
            }
        });

        //Post

        post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(),armiet_news.class);
                startActivity(i);
            }
        });

        //info

        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(),info.class);
                startActivity(i);
            }
        });


        // show firstname
        shownames();


        //data send to editprofile
        //sendDatatoEditprofile();



        btn = findViewById(R.id.logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Dashboard.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

//    private void sendDatatoEditprofile() {
//        String fname = firstname.getText().toString();
//        String lname = lastname.getText().toString();
//        String email = emailid.getText().toString();
//        String phone = phoneno.getText().toString();
//        Intent intent = new Intent(getApplicationContext(),EditProfile.class);
//        intent.putExtra("firstname",fname);
//        intent.putExtra("lastname",lname);
//        intent.putExtra("email",email);
//        intent.putExtra("phone",phone);
//        startActivity(intent);
//    }

    private void shownames() {
        Intent i = getIntent();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    firstname.setText(ds.child("firstname").getValue(String.class));
                    lastname.setText(ds.child("lastname").getValue(String.class));
                    //emailid.setText(ds.child("email").getValue(String.class));
                    //phoneno.setText(ds.child("phone").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Dashboard.this, "their is error in data fetching",
                        Toast.LENGTH_SHORT).show();
            }
        });



    }
}