package com.example.beintouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    TextView emailId,phoneno,dateofbirth,address,firstname,lastname;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    String useremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //hooks
        firstname = findViewById(R.id.Editprofilefirstname);
        lastname = findViewById(R.id.Editprofilelastname);
        emailId = findViewById(R.id.EditprofileEmail);
        phoneno = findViewById(R.id.EditprofilePhone);
        dateofbirth = findViewById(R.id.EditprofileDateofbirth);
        address = findViewById(R.id.EditprofileAddress);



        dataFromDatabase();

    }

    private void dataFromDatabase() {
        Intent i = getIntent();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                        firstname.setText(ds.child("firstname").getValue(String.class));
                        lastname.setText(ds.child("lastname").getValue(String.class));
                        emailId.setText(ds.child("email").getValue(String.class));
                        phoneno.setText(ds.child("phone").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfile.this, "their is error in data fetching",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}