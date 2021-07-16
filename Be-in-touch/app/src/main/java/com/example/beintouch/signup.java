package com.example.beintouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    private TextView register ;
    public EditText firstname,lastname,email,password,phone;
    public Button signupbtn;
    private FirebaseAuth mAuth;
    FirebaseDatabase users;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.signupemail);
        phone = findViewById(R.id.signupphone);
        password = findViewById(R.id.signuppass);
        signupbtn = findViewById(R.id.signupbtn);



        mAuth = FirebaseAuth.getInstance();
        signupbtn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(signup.this,"Fields are Empty!",Toast.LENGTH_SHORT).show();
                }else if(!(emailId.isEmpty() && pass.isEmpty())){
                    mAuth.createUserWithEmailAndPassword(emailId,pass).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isComplete()){
                                Toast.makeText(signup.this,"SignUp UnSuccessful,Please Try Again!",Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent(signup.this,MainActivity.class));
                                adduserdata();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(signup.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
                }
            }
        });




        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openloginform();
            }
        });

    }

    private void adduserdata() {
        users = FirebaseDatabase.getInstance();
        reference = users.getReference("users");
        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String phoneno = phone.getText().toString();
        String emailId = email.getText().toString();
        String pass = password.getText().toString();
        userHelperClass helperClass = new userHelperClass(fname,lname,emailId,pass,phoneno);

        reference.child(phoneno).setValue(helperClass);

    }


    public  void openloginform(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}