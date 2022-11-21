package com.friday.etsfinalone.historyRecyclerView;

public class HistoryObject {
    private String rideId;
    private String time;

    public HistoryObject(String rideId2, String time2) {
        this.rideId = rideId2;
        this.time = time2;
    }

    public String getRideId() {
        return this.rideId;
    }

    public void setRideId(String rideId2) {
        this.rideId = rideId2;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time2) {
        this.time = time2;
    }
}
