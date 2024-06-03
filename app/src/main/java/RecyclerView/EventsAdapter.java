package RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campus.acm.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Session.Events;
import Session.Room;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MeetingViewHolder> {

    private List<Events> events;

    public EventsAdapter(List<Events> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new MeetingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        try {
            Events event = events.get(position);

            String formattedDate = formatDate(event.getDate());
            String formattedTime = formatTime(event.getStart_time()) + " - " + formatTime(event.getEnd_time());
            String roomDetails = formatRoomDetails(event.getRoom());

            holder.eventName.setText(event.getEvent_name());
            holder.time.setText(formattedTime);
            holder.roomDetails.setText(roomDetails);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("EventsAdapter", "Error binding view at position " + position, e);
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName;
        public TextView time;
        public TextView roomDetails;

        public MeetingViewHolder(View view) {
            super(view);
            eventName = view.findViewById(R.id.event_name);
            time = view.findViewById(R.id.time);
            roomDetails = view.findViewById(R.id.room_details);
        }
    }

    private String formatDate(Date date) {
        if (date == null) return "Unknown date";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy, MMM dd");
        return dateFormat.format(date);
    }

    private String formatTime(Date time) {
        if (time == null) return "Unknown time";
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(time);
    }

    private String formatRoomDetails(Room room) {
        if (room == null) return "Unknown room";
        return "Floor " + room.getFloor() + ", " + room.getName();
    }
}
