package com.example.user.biddyv3;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Belal on 2/26/2017.
 */

public class AuctionList extends ArrayAdapter<AuctionDraft> {
    private Activity context;
    List<AuctionDraft> auctionDrafts;
    ListView auctionList;

    public AuctionList(Activity context, List<AuctionDraft> auctionDrafts) {
        super(context, R.layout.activity_post_auction, auctionDrafts);
        this.context = context;
        this.auctionDrafts = auctionDrafts;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_auction_list, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textViewTitle);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        TextView startingPrice = listViewItem.findViewById(R.id.startingPrice);
        Double startingPricE;

        AuctionDraft auctionDraft = auctionDrafts.get(position);
        textViewTitle.setText(auctionDraft.getTitle());
        textViewDesc.setText(auctionDraft.getDescription());
        startingPricE = auctionDraft.getStartingPrice();
        startingPrice.setText(startingPricE.toString());

        return listViewItem;
    }
}
