package RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campus.acm.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import Session.LogItem;

public class LogItemAdapter extends RecyclerView.Adapter<LogItemAdapter.LogItemViewHolder> {
    private List<LogItem> logItemList;
    private SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    private SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

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

    public void addItems(List<LogItem> newLogItems) {
        logItemList.addAll(newLogItems);
        notifyDataSetChanged();
    }

    public static class LogItemViewHolder extends RecyclerView.ViewHolder {
        private TextView attendeeTextView;
        private TextView roleTextView;
        private TextView loginTimeTextView;
        private TextView logoutTimeTextView;

        public LogItemViewHolder(@NonNull View itemView) {
            super(itemView);
            attendeeTextView = itemView.findViewById(R.id.atteee);
            roleTextView = itemView.findViewById(R.id.rolee);
            loginTimeTextView = itemView.findViewById(R.id.logintime);
            logoutTimeTextView = itemView.findViewById(R.id.logouttime);
        }

        public void bind(LogItem logItem) {
            attendeeTextView.setText(logItem.isAttended() ? "true" : "false");
            roleTextView.setText(logItem.getRole());

            // Convert timestamps to a human-readable format
            loginTimeTextView.setText(formatTime(logItem.getLoginTime()));
            logoutTimeTextView.setText(formatTime(logItem.getLogoutTime()));
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
    }}
