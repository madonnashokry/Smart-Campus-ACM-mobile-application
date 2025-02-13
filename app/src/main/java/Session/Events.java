package Session;

import java.util.List;

public class Events {

   int event_id;
   String event_name ;
   String type ;
 String date;
    String start_time;
    String room_id;
    String  course_id;
     String end_time;
    Course course ;
    user organizer ;
    Room room ;
    String room_name;
    private String course_name;
    String organizer_email;
    List<user> attendees ;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getOrganizer_email() {
        return organizer_email;
    }

    public void setOrganizer_email(String organizer_email) {
        this.organizer_email = organizer_email;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDate() {
        return date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

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
    //private boolean attended;
    private String Login_time;
    private String Logout_time;
    private boolean attended;
    // Getters and setters for the new fields


    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public String getLogin_time() {
        return Login_time;
    }

    public void setLogin_time(String login_time) {
        this.Login_time = login_time;
    }

    public String getLogout_time() {
        return Logout_time;
    }

    public void setLogout_time(String logout_time) {
        this.Logout_time = logout_time;
    }
}
