package com.friday.etsfinalone;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.friday.etsfinalone.historyRecyclerView.HistoryAdapter;
import com.friday.etsfinalone.historyRecyclerView.HistoryObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {
    private String customerOrDriver;
    /* access modifiers changed from: private */
    public RecyclerView.Adapter mHistoryAdapter;
    private RecyclerView.LayoutManager mHistoryLayoutManager;
    private RecyclerView mHistoryRecyclerView;
    /* access modifiers changed from: private */
    public ArrayList resultsHistory = new ArrayList();
    private String userId;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1102R.layout.res_history);
        this.mHistoryRecyclerView = (RecyclerView) findViewById(C1102R.C1105id.historyRecyclerView);
        this.mHistoryRecyclerView.setNestedScrollingEnabled(false);
        this.mHistoryRecyclerView.setHasFixedSize(true);
        this.mHistoryLayoutManager = new LinearLayoutManager(this);
        this.mHistoryRecyclerView.setLayoutManager(this.mHistoryLayoutManager);
        this.mHistoryAdapter = new HistoryAdapter(getDataSetHistory(), this);
        this.mHistoryRecyclerView.setAdapter(this.mHistoryAdapter);
        ((ImageButton) findViewById(C1102R.C1105id.back_button_4)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryActivity historyActivity = HistoryActivity.this;
                historyActivity.startActivity(new Intent(historyActivity.getApplicationContext(), ResponderMapActivity.class).setFlags(67108864));
            }
        });
        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getUserHistoryIds();
    }

    private void getUserHistoryIds() {
        DatabaseReference userHistoryDatabase = FirebaseDatabase.getInstance().getReference();
        userHistoryDatabase.child("Users").child("Drivers").child(this.userId).child("history");
        userHistoryDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot history : dataSnapshot.getChildren()) {
                        HistoryActivity.this.FetchRideInformation(history.getKey());
                    }
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void FetchRideInformation(String rideKey) {
        FirebaseDatabase.getInstance().getReference().child("history").child(rideKey).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String rideId = dataSnapshot.getKey();
                    long timestamp = 0L;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getKey().equals("timestamp")) {
                            timestamp = Long.valueOf(child.getValue().toString());
                        }
                    }
                    HistoryActivity.this.resultsHistory.add(new HistoryObject(rideId, HistoryActivity.this.getDate(timestamp)));
                    HistoryActivity.this.mHistoryAdapter.notifyDataSetChanged();
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /* access modifiers changed from: private */
    public String getDate(Long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time.longValue() * 1000);
        return DateFormat.format("MM-dd-yyyy hh:mm", cal).toString();
    }

    private ArrayList<HistoryObject> getDataSetHistory() {
        return this.resultsHistory;
    }
}
