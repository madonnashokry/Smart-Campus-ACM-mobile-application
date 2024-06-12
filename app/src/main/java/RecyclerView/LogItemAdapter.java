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
            loginTimeTextView.setText(logItem.getLoginTime());
            logoutTimeTextView.setText(logItem.getLogoutTime());
        }
    }
}
