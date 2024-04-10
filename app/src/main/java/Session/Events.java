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

}
