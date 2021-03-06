package com.example.user.biddyv3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername, editTextName, editTextEmail, editTextPassword, editTextContact, editTextAddress;
    Button buttonSignup;
    TextView textViewSignin;

    List<User> users;

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        //getting the reference of users node
        databaseUser = FirebaseDatabase.getInstance().getReference("users");

        //getting views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextContact = (EditText) findViewById(R.id.editTextContact);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        //list to store users
        users = new ArrayList<>(); //should be in login, not used here.

        //adding an onclicklistener to button
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == buttonSignup) {
                    addUser();
                }
            }
        });

        textViewSignin.setOnClickListener(new View. OnClickListener(){
            public void onClick(View view) {
                if (view == textViewSignin) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
    }

    private void addUser() {
        //getting the values to save
        String username = editTextUsername.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String emailAddress = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String contactNo = editTextContact.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our User
            String userID = databaseUser.push().getKey();

            //creating an User Object
            User users = new User (userID, username, name, emailAddress, password, contactNo, address);

            //Saving the User
            databaseUser.child(userID).setValue(users);

            //setting edittext to blank again
            editTextUsername.setText("");
            editTextName.setText("");
            editTextEmail.setText("");
            editTextPassword.setText("");
            editTextContact.setText("");
            editTextAddress.setText("");

            //displaying a success toast
            Toast.makeText(this, "You are now registered!", Toast.LENGTH_SHORT).show();

            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please complete the register form", Toast.LENGTH_SHORT).show();
        }
    }
}
