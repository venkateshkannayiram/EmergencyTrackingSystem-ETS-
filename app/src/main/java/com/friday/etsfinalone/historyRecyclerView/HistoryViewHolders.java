package com.friday.etsfinalone.historyRecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.friday.etsfinalone.C1102R;
import com.friday.etsfinalone.HistorySingleActivity;

public class HistoryViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView rideId;
    public TextView time;

    public HistoryViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.rideId = (TextView) itemView.findViewById(C1102R.C1105id.rideId);
        this.time = (TextView) itemView.findViewById(C1102R.C1105id.time);
    }

    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), HistorySingleActivity.class);
        Bundle b = new Bundle();
        b.putString("rideId", this.rideId.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);
    }
}
