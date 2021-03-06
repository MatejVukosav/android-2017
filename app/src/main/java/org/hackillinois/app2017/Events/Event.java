package org.hackillinois.app2017.Events;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Event {

    @SerializedName("locations")
    private ArrayList<EventLocation> locations;

    @SerializedName("description")
    private String description;

    @SerializedName("startTime")
    private Date startTime;

    @SerializedName("endTime")
    private Date endTime;

    @SerializedName("name")
    private String name;

    @SerializedName("shortName")
    private String shortName;

    @SerializedName(value="tracking", alternate={"qrCode"})
    //qrCode is no longer the name in the api (this is just a temporary backup)
    private int tracking;

    public Event(String name, Date start, Date end, ArrayList<EventLocation> locations) {
        this.name = name;
        startTime = start;
        endTime = end;
        this.locations = locations;
        tracking = 1;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public ArrayList<EventLocation> getLocation(){ //TODO CHECK THIS
        return locations;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getStartHour() {
        DateFormat format = new SimpleDateFormat("h:mm a");
        return format.format(startTime);

    }

    public String getDescription() { return description; }

    public int getStartDay() {
        return startTime.getDay();
    }

    public Date getEndTime() {
        return endTime;
    }

    public boolean needsQRCode() {
        return tracking != 0;
    }

    public void fixTime() {
        startTime.setTime(startTime.getTime() - TimeUnit.HOURS.toMillis(6));
        endTime.setTime(endTime.getTime() - TimeUnit.HOURS.toMillis(6));
    }
}
