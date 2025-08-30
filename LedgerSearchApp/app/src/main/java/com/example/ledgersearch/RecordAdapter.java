
package com.example.ledgersearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private List<Map<String, String>> originalList;
    private List<Map<String, String>> filteredList;

    public RecordAdapter(List<Map<String, String>> records) {
        this.originalList = new ArrayList<>(records);
        this.filteredList = records;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, String> record = filteredList.get(position);
        StringBuilder details = new StringBuilder();
        String title = "";
        for (Map.Entry<String, String> entry : record.entrySet()) {
            if (title.isEmpty()) {
                title = entry.getValue();
            }
            details.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        holder.title.setText(title);
        holder.details.setText(details.toString());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String query) {
        filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (Map<String, String> record : originalList) {
                for (String value : record.values()) {
                    if (value.toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(record);
                        break;
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, details;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            details = itemView.findViewById(R.id.details);
        }
    }
}
