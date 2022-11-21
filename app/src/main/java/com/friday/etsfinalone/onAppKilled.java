package com.friday.etsfinalone;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.firebase.geofire.GeoFire;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class onAppKilled extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        new GeoFire(FirebaseDatabase.getInstance().getReference("ResponderAvailable")).removeLocation(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }
}
