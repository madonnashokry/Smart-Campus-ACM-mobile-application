package Session;

public class LogItem {
    private int participantId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String role;
    private String eventName;
    private String courseName;

    private int Login_time;  // Store as seconds since midnight
    private int Logout_time;
    private boolean attended;
    private String duration;

    public int getLogin_time() {
        return Login_time;
    }

    public void setLogin_time(int login_time) {
        Login_time = login_time;
    }

    public int getLogout_time() {
        return Logout_time;
    }

    public void setLogout_time(int logout_time) {
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

    public LogItem(int participantId, String firstName, String middleName, String lastName, String role, int loginTime, int logoutTime, boolean attended) {

        this.participantId = participantId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.role = role;
        this.Login_time = loginTime;
        this.Logout_time = logoutTime;
        this.attended = attended;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
