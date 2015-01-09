package se.ESNBTH.esnbth.RequestHelper;

import java.util.ArrayList;

/**
 * Created by Julio on 08/01/2015.
 */
public class Event {
    private String id;
    private String name;
    private String description;
    private String location;
    private String startTime;
    private String imgUrl;

    public Event(){};

    public Event(Event event){
        this.id = event.getId();
        this.name=event.getName();
        this.description=event.description;
        this.location=event.getLocation();
        this.startTime=event.getStartTime();
        this.imgUrl=event.getImgUrl();
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public Event mergeInfoEvents(Event event1){
        //Event 1 Has all the data except of the imgUrl
        //Event 2 Has only the imgURL
        Event result = new Event();
        result.setId(event1.getId());
        result.setName(event1.getName());
        result. setDescription(event1.getDescription());
        result.setLocation(event1.getLocation());
        result.setStartTime(event1.getStartTime());

        return result;
    }
}
