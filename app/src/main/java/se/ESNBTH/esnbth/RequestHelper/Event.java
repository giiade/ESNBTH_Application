package se.ESNBTH.esnbth.RequestHelper;

/**
 * Created by Julio on 08/01/2015.
 */
public class Event {
    private String id;
    private String name;
    private String description;
    private String location;
    private String startTime;

    public Event(){};

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
}
