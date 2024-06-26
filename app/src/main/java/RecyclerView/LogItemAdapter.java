package RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campus.acm.R;

import java.util.List;

import Session.LogItem;

public class LogItemAdapter extends RecyclerView.Adapter<LogItemAdapter.LogItemViewHolder> {
    private List<LogItem> logItemList;

    public LogItemAdapter(List<LogItem> logItemList) {
        this.logItemList = logItemList;
    }

    @NonNull
    @Override
    public LogItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item_view, parent, false);
        return new LogItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogItemViewHolder holder, int position) {
        LogItem logItem = logItemList.get(position);
        holder.bind(logItem);
    }

    @Override
    public int getItemCount() {
        return logItemList.size();
    }

    public static class LogItemViewHolder extends RecyclerView.ViewHolder {
        private TextView eventNameTextView;
        private TextView courseNameTextView;
        private TextView attendeeTextView;
        private TextView roleTextView;
        private TextView loginTimeTextView;
        private TextView logoutTimeTextView;
        TextView firstName, middleName, lastName, participantId;

        public LogItemViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.event_name);
            courseNameTextView = itemView.findViewById(R.id.course_name);
            attendeeTextView = itemView.findViewById(R.id.atteee);

            loginTimeTextView = itemView.findViewById(R.id.logintime);
            logoutTimeTextView = itemView.findViewById(R.id.logouttime);
            firstName = itemView.findViewById(R.id.firstname);
            middleName = itemView.findViewById(R.id.middlname);
            lastName = itemView.findViewById(R.id.lastname);
            participantId = itemView.findViewById(R.id.participant_id);
        }

        public void bind(LogItem logItem) {
            eventNameTextView.setText(logItem.getEventName());
            courseNameTextView.setText(logItem.getCourseName());
            attendeeTextView.setText(logItem.isAttended() ? "true" : "false");
            loginTimeTextView.setText(formatTime(logItem.getLogin_time()));
            logoutTimeTextView.setText(formatTime(logItem.getLogout_time()));
           firstName.setText(logItem.getFirst_name());
          middleName.setText(logItem.getMiddle_name());
           lastName.setText(logItem.getLast_name());
         // participantId.setText(String.valueOf(logItem.getParticipant_id()));


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
}
