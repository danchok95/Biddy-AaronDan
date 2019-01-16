package com.example.user.biddyv3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostAuction extends AppCompatActivity {

    //a constant to track the file chooser intent
    static final int PICK_IMAGE_REQUEST = 234;

    EditText editTextTitle, editTextBrand, editTextModel, editTextDesc, editTextStartPrice, editTextMinBid, editTextBINPrice;
    Button continueBtn, pickPicBtn;
    Spinner spinnerCondition, spinnerDays, spinnerHours;
    String condition;
    Integer days, hours;
    ImageView imageView;
    //a Uri object to store file path
    Uri filePath;

    StorageReference storageReference;

    List<AuctionDraft> AuctionDrafts;

    DatabaseReference databaseAuctionDraft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_auction);

        getSupportActionBar().hide();

        //getting the reference of users node
        databaseAuctionDraft = FirebaseDatabase.getInstance().getReference("auctionDrafts");
        storageReference = FirebaseStorage.getInstance().getReference();

        //getting views
        pickPicBtn = findViewById(R.id.pickPicBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextBrand = (EditText) findViewById(R.id.editTextBrand);
        editTextModel = (EditText) findViewById(R.id.editTextModel);
        editTextDesc = (EditText) findViewById(R.id.editTextDesc);
        editTextStartPrice = (EditText) findViewById(R.id.editTextStartPrice);
        editTextMinBid = (EditText) findViewById(R.id.editTextMinBid);
        editTextBINPrice = findViewById(R.id.editTextBINPrice);
        continueBtn = (Button) findViewById(R.id.continueBtn);
        spinnerCondition = (Spinner) findViewById(R.id.spinnerCondition);
        spinnerDays = (Spinner) findViewById(R.id.spinnerDays);
        spinnerHours = (Spinner) findViewById(R.id.spinnerHours);
        CheckBox BINcheck = (CheckBox) findViewById(R.id.BINcheck);
        BINcheck.setChecked(false);
        editTextBINPrice.setEnabled(false);
        editTextBINPrice.setFocusable(false);
        editTextBINPrice.setFocusableInTouchMode(false);



        //list to store users
        AuctionDrafts = new ArrayList<>(); //should be in login, not used here.

        //adding an onclicklistener to button
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == continueBtn) {
                    continueDraft();
                    uploadFile();
                }
            }
        });

        pickPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == pickPicBtn) {
                    showFileChooser();
                }
            }
        });

        BINcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    // Do your coding
                    editTextBINPrice.setEnabled(true);
                    editTextBINPrice.setFocusable(true);
                    editTextBINPrice.setFocusableInTouchMode(true);
                }
                if (!checked){
                    // Do your coding
                    editTextBINPrice.setEnabled(false);
                    editTextBINPrice.setFocusable(false);
                    editTextBINPrice.setFocusableInTouchMode(false);
                }
            }
        });

        spinnerCondition.setOnItemSelectedListener(new ConditionSelectedListener());
        spinnerDays.setOnItemSelectedListener(new DaysSelectedListener());
        spinnerHours.setOnItemSelectedListener(new HoursSelectedListener());

    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //this method will upload the file
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            StorageReference riversRef = storageReference.child("images/watchPic.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Image Uploaded ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }

    public class ConditionSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinnerCondition.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinnerCondition.getSelectedItem()))) {
                // ToDo when first item is selected
                condition = parent.getItemAtPosition(pos).toString();

            } else {
                // Todo when item is selected by the user
                condition = parent.getItemAtPosition(pos).toString();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }

    public class DaysSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinnerCondition.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinnerCondition.getSelectedItem()))) {
                // ToDo when first item is selected
                String dayS = parent.getItemAtPosition(pos).toString();
                days = Integer.parseInt(dayS);

            } else {
                // Todo when item is selected by the user
                String dayS = parent.getItemAtPosition(pos).toString();
                days = Integer.parseInt(dayS);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }

    public class HoursSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinnerCondition.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinnerCondition.getSelectedItem()))) {
                // ToDo when first item is selected
                String hourS = parent.getItemAtPosition(pos).toString();
                hours = Integer.parseInt(hourS);

            } else {
                // Todo when item is selected by the user
                String hourS = parent.getItemAtPosition(pos).toString();
                hours = Integer.parseInt(hourS);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }


    private void continueDraft() {
        //getting the values to save
        String title = editTextTitle.getText().toString().trim();
        String brand = editTextBrand.getText().toString().trim();
        String model = editTextModel.getText().toString().trim();
        String condition = this.condition;
        String description = editTextDesc.getText().toString().trim();
        String startingPricE = editTextStartPrice.getText().toString().trim();
        Double startingPrice = Double.parseDouble(startingPricE);
        String minBidIncR = editTextMinBid.getText().toString().trim();
        int minBidIncr = Integer.parseInt(minBidIncR);
        String BINprice = editTextBINPrice.getText().toString().trim();
        int days = this.days;
        int hours = this.hours;



        //checking if the value is provided
        if (!TextUtils.isEmpty(title)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Auction Draft
            String auctionID = databaseAuctionDraft.push().getKey();

            //creating an Auction Draft Object
            AuctionDraft auctionDraft = new AuctionDraft (auctionID, title, brand, model, condition, description, startingPrice, minBidIncr, BINprice, days, hours);

            //Saving the Auction Draft
            databaseAuctionDraft.child(auctionID).setValue(auctionDraft);

            //setting edittext to blank again
            editTextTitle.setText("");
            editTextBrand.setText("");
            editTextModel.setText("");
            editTextDesc.setText("");
            editTextStartPrice.setText("");
            editTextMinBid.setText("");
            editTextBINPrice.setText("");


            //displaying a success toast
            Toast.makeText(this, "Auction posted!", Toast.LENGTH_SHORT).show();

            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please fill up the auction form", Toast.LENGTH_SHORT).show();
        }
    }
}
