package Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class eventsfilter {
    public static List<Events> getPreviousEvents(List<Events> allEvents) {
        List<Events> previousEvents = new ArrayList<>();
        Date currentDate = new Date();
        for (Events event : allEvents) {
            if (event.getDate().before(currentDate)) {
                previousEvents.add(event);
            }
        }
        return previousEvents;
    }

    public static List<Events> getUpcomingEvents(List<Events> allEvents) {
        List<Events> upcomingEvents = new ArrayList<>();
        Date currentDate = new Date();
        for (Events event : allEvents) {
            if (event.getDate().after(currentDate)) {
                upcomingEvents.add(event);
            }
        }
        return upcomingEvents;
    }
}

