package com.example.beintouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity<email, password> extends AppCompatActivity {
    public static String TAG = "FIREBASEAUTHENTICATION";
    public TextView register ;
    private Button loginbtn;
    public EditText email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpass);
        register = findViewById(R.id.register);
        loginbtn = findViewById(R.id.Loginbtn);
        mAuth = FirebaseAuth.getInstance();


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId = email.getText().toString();
                String pass = password.getText().toString();
                if(emailId.isEmpty()){
                    email.setError("Please Enter Your Email-id");
                    email.requestFocus();
                }else if(pass.isEmpty()){
                    password.setError("Please Enter Your Password");
                    password.requestFocus();

                }else if (emailId.isEmpty() && pass.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields are Empty!",Toast.LENGTH_SHORT).show();
                }else if  (!(emailId.isEmpty() && pass.isEmpty())){
                    mAuth.signInWithEmailAndPassword(emailId, pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(MainActivity.this, "Authentication Successfull.",
                                                Toast.LENGTH_SHORT).show();
                                        opendashboard();
                                        updateUI(user);
                                        getDataFromDatabase();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }else{
                    Toast.makeText(MainActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
                }
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensignupform();
            }
        });
    }

    private void getDataFromDatabase() {
        final String useremail = email.getText().toString();
        final String userpass = password.getText().toString();
        // Dashboard setting
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("firstname").equalTo(userpass);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    email.setError(null);

                    String userpassworddb = snapshot.child(useremail).child("email").getValue(String.class);
                    if(userpassworddb.equals(userpass)){
                        email.setError(null);
                        String fnamedb = snapshot.child(useremail).child("firstname").getValue(String.class);
                        String lnamedb = snapshot.child(useremail).child("lastname").getValue(String.class);
                        String emaildb = snapshot.child(useremail).child("email").getValue(String.class);
                        String phonedb = snapshot.child(useremail).child("phone").getValue(String.class);
                        if(fnamedb.isEmpty()){
                            Toast.makeText(MainActivity.this, "fnamedb is empty",
                                    Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                        intent.putExtra("firstname",fnamedb);
                        intent.putExtra("lastname",lnamedb);
                        intent.putExtra("email",emaildb);
                        intent.putExtra("phone",phonedb);
                        intent.putExtra("password",userpassworddb);
                        startActivity(intent);
                        Intent i = new Intent(getApplicationContext(),EditProfile.class);
                        intent.putExtra("firstname",fnamedb);
                        intent.putExtra("lastname",lnamedb);
                        intent.putExtra("email",emaildb);
                        intent.putExtra("phone",phonedb);
                        intent.putExtra("password",userpassworddb);
                        startActivity(i);
                    }
                    else {
                        password.setError("Incorrect password");
                        password.requestFocus();
                    }

                }else{
                    email.setError("No such user Exist");
                    email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Database Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser) {

    }

    public  void opensignupform() {
        Intent intent = new Intent(MainActivity.this, signup.class);
        startActivity(intent);
    }
    public void opendashboard(){
        Intent intent = new Intent(MainActivity.this,Dashboard.class);
        startActivity(intent);
    }
}