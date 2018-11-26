package com.example.user.biddyv3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PostAuction extends AppCompatActivity {

    EditText editTextTitle, editTextBrand, editTextModel, editTextDesc, editTextStartPrice, editTextMinBid, editTextBINPrice;
    Button continueBtn;
    Spinner spinnerCondition, spinnerDays, spinnerHours;
    String condition;
    Integer days, hours;

    List<AuctionDraft> AuctionDrafts;

    DatabaseReference databaseAuctionDraft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_auction);

        getSupportActionBar().hide();

        //getting the reference of users node
        databaseAuctionDraft = FirebaseDatabase.getInstance().getReference("auctionDrafts");

        //getting views
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
            Toast.makeText(this, "Auction drafted", Toast.LENGTH_SHORT).show();

            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please fill up the auction form", Toast.LENGTH_SHORT).show();
        }
    }
}
