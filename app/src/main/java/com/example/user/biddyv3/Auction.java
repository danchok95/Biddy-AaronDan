package com.example.user.biddyv3;

public class Auction {

    private String auctionID, userID, watchID, title, description;
    private Integer minBidIncr;
    private double startingPrice;

    public Auction() {}

    public Auction(String auctionID, String userID, String watchID, String title, String description, Integer minBidIncr, double startingPrice) {
        this.auctionID = auctionID;
        this.userID = userID;
        this.watchID = watchID;
        this.title = title;
        this.description = description;
        this.minBidIncr = minBidIncr;
        this.startingPrice = startingPrice;
    }

    public String getAuctionID() {
        return auctionID;
    }

    public String getUserID() { return userID; }

    public String getWatchID() { return watchID; }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getMinBidIncr() {
        return minBidIncr;
    }

    public double getStartingPrice() { return startingPrice; }

}