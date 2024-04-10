package Session;

public class user {
    int id ;
    String first_name;
    String middle_name;
    String last_name;
    String ssn;
    String email ;
    String password ;
    String role ;




  //public void user()
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }


    public String getMiddle_name() {
        return middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getId() {
        return id;
    }
    
}
