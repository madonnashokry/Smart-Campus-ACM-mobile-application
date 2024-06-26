package RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campus.acm.R;
import com.campus.acm.optionsviews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Session.Events;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MeetingViewHolder> {
    private List<Events> events;
    private Context context;
    private static final String TAG = "EventsAdapter";
    private boolean isPlannedMeetingsActivity;

    public EventsAdapter(List<Events> events, Context context) {
        this.events = events;
        this.context = context;
    }

    // Setter method for isPlannedMeetingsActivity
    public void setPlannedMeetingsActivity(boolean isPlannedMeetingsActivity) {
        this.isPlannedMeetingsActivity = isPlannedMeetingsActivity;
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
            String roomName = event.getRoom_name();

            Log.d(TAG, "Binding event: " + event.getEvent_name() + ", Room Name: " + roomName);  // Log the room name

            holder.eventName.setText(event.getEvent_name());
            holder.date.setText(formattedDate);
            holder.time.setText(formattedTime);
            holder.room_name.setText(roomName);

            // Check if in PlannedMeetingsActivity before setting the click listener
            if (isPlannedMeetingsActivity) {
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, optionsviews.class);
                    intent.putExtra("event_id", event.getEvent_id());
                    context.startActivity(intent);
                });
            } else {
                holder.itemView.setOnClickListener(null); // or set a different listener if needed
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error binding view at position " + position, e);
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName;
        public TextView date;
        public TextView time;
        public TextView room_name;

        public MeetingViewHolder(View view) {
            super(view);
            eventName = view.findViewById(R.id.event_name);
            date = view.findViewById(R.id.date);
            time = view.findViewById(R.id.time);
            room_name = view.findViewById(R.id.room_details);
        }
    }

    private String formatDate(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy, MMM dd");
            Date parsedDate = inputFormat.parse(date);
            return outputFormat.format(parsedDate);
        } catch (Exception e) {
            return "Unknown date";
        }
    }

    private String formatTime(String duration) {
        try {
            long seconds = Long.parseLong(duration.replace("PT", "").replace("S", ""));
            long hours = seconds / 3600;
            long minutes = (seconds % 3600) / 60;
            return String.format("%02d:%02d", hours, minutes);
        } catch (Exception e) {
            return "Unknown time";
        }
    }
}
