package com.example.user.biddyv3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AuctionPostSummary extends AppCompatActivity {

    TextView title, brand, model, condition, description, startingPrice, minBidIncr, BINprice, days, hours;
    Double startingPricE, BINpricE;
    int minBidIncR, dayS, hourS;
    String startingPriCE, minBidInCR, BINpriCE, daYS, houRS;
    Button deleteBtn;

    //our database reference object
    DatabaseReference databaseAuctionDrafts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_post_summary);
        getSupportActionBar().hide();

        //getting the reference of artists node
        databaseAuctionDrafts = FirebaseDatabase.getInstance().getReference("auctionDrafts");

        title = findViewById(R.id.title);
        brand = findViewById(R.id.brand);
        model = findViewById(R.id.model);
        condition = findViewById(R.id.condition);
        description = findViewById(R.id.description);
        startingPrice = findViewById(R.id.startingPrice);
        minBidIncr = findViewById(R.id.minBidIncr);
        BINprice = findViewById(R.id.BINprice);
        days = findViewById(R.id.days);
        hours = findViewById(R.id.hours);
        deleteBtn = findViewById(R.id.deleteBtn);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //get the current intent
        Intent intent = getIntent();
        //get the attached extras from the intent
        //we should use the same key as we used to attach the data.
        final String auctionID = intent.getStringExtra("AUCTION_ID");
        Toast.makeText(AuctionPostSummary.this, auctionID, Toast.LENGTH_SHORT).show();
        displayAuctionDetails(auctionID);

        //adding an onclicklistener to button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == deleteBtn){
                    deleteAuction(auctionID);
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            }
        });
    }


    public void displayAuctionDetails(String auctionID) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("auctionDrafts").child(auctionID);

        // Attach a listener to read the data at our posts reference
        dR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AuctionDraft auctionDraft = dataSnapshot.getValue(AuctionDraft.class);
                title.setText(auctionDraft.getTitle());
                brand.setText(auctionDraft.getBrand());
                model.setText(auctionDraft.getModel());
                condition.setText(auctionDraft.getCondition());
                description.setText(auctionDraft.getDescription());
                startingPricE = auctionDraft.getStartingPrice();
                startingPriCE = Double.toString(startingPricE);
                startingPrice.setText(startingPriCE);
                minBidIncR = auctionDraft.getMinBidIncr();
                minBidInCR = Integer.toString(minBidIncR);
                minBidIncr.setText(minBidInCR);
                BINprice.setText(auctionDraft.getBINprice());
                dayS = auctionDraft.getDays();
                daYS = Integer.toString(dayS);
                days.setText(daYS);
                hourS = auctionDraft.getHours();
                houRS = Integer.toString(hourS);
                hours.setText(houRS);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private boolean deleteAuction(String auctionID) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("auctionDrafts").child(auctionID );

        //removing artist
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Auction Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }
}
