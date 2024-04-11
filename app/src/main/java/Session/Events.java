package Session;

import java.util.Date;
import java.util.List;

public class Events {

   int event_id;
   String event_name ;
   String type ;
    Date date ;
    Date start_time ;
    Date    end_time ;
    Course course ;
    user organizer ;
    Room room ;
    List<user> attendees ;

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<user> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<user> attendees) {
        this.attendees = attendees;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public user getOrganizer() {
        return organizer;
    }

    public void setOrganizer(user organizer) {
        this.organizer = organizer;
    }
}
