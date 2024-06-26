package Session;

public class LogItem {
    private int participant_id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String role;
    private String eventName;
    private String courseName;

    private String Login_time;  // Store as seconds since midnight
    private String Logout_time;
    private boolean attended;
    private String duration;

    public String getLogin_time() {
        return Login_time;
    }

    public void setLogin_time(String login_time) {
        Login_time = login_time;
    }

    public String getLogout_time() {
        return Logout_time;
    }

    public void setLogout_time(String logout_time) {
        Logout_time = logout_time;
    }

    public LogItem() {
    }

    // Parameterized constructor

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(int participant_id) {
        this.participant_id = participant_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
