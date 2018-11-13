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

    public static final String USER_ID = ".userID";
    public static final String USERNAME = ".username";
    public static final String NAME = ".name";
    public static final String EMAIL_ADD = ".emailAddress";
    public static final String PASSWORD = ".password";
    public static final String CONTACT_NO = ".contactNo";
    public static final String ADDRESS = ".address";

    EditText editTextUsername, editTextName, editTextEmail, editTextPassword, editTextContact, editTextAddress;
    Button buttonSignup;
    TextView textViewSignin;

    List<User> users;

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //getting the reference of artists node
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

        //list to store artists
        users = new ArrayList<>();

        //adding an onclicklistener to button
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == buttonSignup) {
                    addUser();
                }

                if (view == textViewSignin) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
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
        int contactNO = Integer.parseInt(contactNo);
        String address = editTextAddress.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String userID = databaseUser.push().getKey();

            //creating an Artist Object
            User users = new User (userID, username, name, emailAddress, password, contactNO, address);

            //Saving the Artist
            databaseUser.child(userID).setValue(users);

            //setting edittext to blank again
            editTextUsername.setText("");
            editTextName.setText("");
            editTextEmail.setText("");
            editTextPassword.setText("");
            editTextContact.setText("");
            editTextAddress.setText("");

            //displaying a success toast
            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();

            finish();
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please complete the register form", Toast.LENGTH_LONG).show();
        }
    }
}
