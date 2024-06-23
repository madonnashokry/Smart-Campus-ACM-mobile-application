package Session;

public class LogItem {
    private int participantId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String role;

    private String Login_time;
    private String Logout_time;
    private boolean attended;
    private String duration;


    public LogItem(int participantId, String firstName, String middleName, String lastName, String role, String loginTime, String logoutTime, boolean attended) {

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

    public String getLoginTime() {
        return Login_time;
    }

    public void setLoginTime(String loginTime) {
        this.Login_time = loginTime;
    }

    public String getLogoutTime() {
        return Logout_time;
    }

    public void setLogoutTime(String logoutTime) {
        this.Logout_time = logoutTime;
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
