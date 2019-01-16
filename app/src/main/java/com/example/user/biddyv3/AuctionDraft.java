package com.example.user.biddyv3;

public class AuctionDraft {

    private String auctionID, title, brand, model, condition, description, BINprice;
    private Integer minBidIncr, days, hours;
    private double startingPrice;

    public AuctionDraft() {}

    public AuctionDraft(String auctionID, String title, String brand, String model, String condition, String description, double startingPrice, Integer minBidIncr, String BINprice, Integer days, Integer hours) {
        this.auctionID = auctionID;
        this.title = title;
        this.brand = brand;
        this.model = model;
        this.condition = condition;
        this.description = description;
        this.minBidIncr = minBidIncr;
        this.startingPrice = startingPrice;
        this.BINprice = BINprice;
        this.days = days;
        this.hours = hours;
    }

    public String getAuctionID() {
        return auctionID;
    }

    //public String getUserID() { return userID; }

    //public String getWatchID() { return watchID; }

    public String getBrand() { return brand; }

    public String getModel() { return model; }

    public String getCondition() { return condition; }

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

    public String getBINprice() { return BINprice; }

    public Integer getDays() { return days; }

    public Integer getHours() { return hours; }

}
