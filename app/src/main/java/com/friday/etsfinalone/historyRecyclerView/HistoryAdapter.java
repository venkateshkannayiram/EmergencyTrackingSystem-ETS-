package com.friday.etsfinalone.historyRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.friday.etsfinalone.C1102R;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolders> {
    private Context context;
    private List<HistoryObject> itemList;

    public HistoryAdapter(List<HistoryObject> itemList2, Context context2) {
        this.itemList = itemList2;
        this.context = context2;
    }

    public HistoryViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(C1102R.layout.item_history, (ViewGroup) null, false);
        layoutView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        return new HistoryViewHolders(layoutView);
    }

    public void onBindViewHolder(HistoryViewHolders holder, int position) {
        holder.rideId.setText(this.itemList.get(position).getRideId());
        if (this.itemList.get(position).getTime() != null) {
            holder.time.setText(this.itemList.get(position).getTime());
        }
    }

    public int getItemCount() {
        return this.itemList.size();
    }
}
