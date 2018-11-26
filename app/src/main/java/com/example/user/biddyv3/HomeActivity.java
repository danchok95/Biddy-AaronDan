package com.example.user.biddyv3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    //view objects
    ListView listViewAuctions;
    CardView cardView;
    TextView textViewTitle, textViewDesc;

    //our database reference object
    DatabaseReference databaseAuctionDrafts;

    //a list to store all the artist from firebase database
    List<AuctionDraft> auctionDrafts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //getting the reference of artists node
        databaseAuctionDrafts = FirebaseDatabase.getInstance().getReference("auctionDrafts");

        //getting views
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewDesc = (TextView) findViewById(R.id.textViewDesc);
        cardView = (CardView) findViewById(R.id.cardView);
        listViewAuctions = findViewById(R.id.listViewAuctions);

        //list to store artists
        auctionDrafts = new ArrayList<>();

        listViewAuctions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AuctionDraft auctionDraft = auctionDrafts.get(i);
                showUpdateDeleteDialog(auctionDraft.getAuctionID(), auctionDraft.getTitle());
                return true;
            }
        });

        listViewAuctions.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AuctionDraft auctionDraft = auctionDrafts.get(position);
                Toast.makeText(getBaseContext(),auctionDraft.getTitle(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), AuctionPostSummary.class);
                //attach the key value pair using putExtra to this intent
                String auctionID = auctionDraft.getAuctionID();
                intent.putExtra("AUCTION_ID", auctionID);
                startActivity(intent);
            }
        });


        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
                        Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        return true;
                    case R.id.postAuction:
                        Toast.makeText(HomeActivity.this, "Going to auction", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), PostAuction.class));
                        return true;
                    case R.id.logout:
                        Toast.makeText(HomeActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseAuctionDrafts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                auctionDrafts.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    AuctionDraft auctionDraft = postSnapshot.getValue(AuctionDraft.class);
                    //adding artist to the list
                    auctionDrafts.add(auctionDraft);
                }

                //creating adapter
                AuctionList auctionAdapter = new AuctionList(HomeActivity.this, auctionDrafts);
                //attaching adapter to the listview
                listViewAuctions.setAdapter(auctionAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private boolean deleteAuction(String auctionID) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("auctionDrafts").child(auctionID );

        //removing artist
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Auction Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void showUpdateDeleteDialog(final String auctionID, String title) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_auction_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextDesc = (EditText) dialogView.findViewById(R.id.editTextDesc);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteBtn);

        dialogBuilder.setTitle(title);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAuction(auctionID);
                b.dismiss();
            }
        });
    }
}