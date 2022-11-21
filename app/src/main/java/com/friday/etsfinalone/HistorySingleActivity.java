package com.friday.etsfinalone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HistorySingleActivity extends AppCompatActivity implements OnMapReadyCallback, RoutingListener {
    private static final int[] COLORS = {C1102R.C1103color.primary_dark_material_light};
    /* access modifiers changed from: private */
    public String currentUserId;
    /* access modifiers changed from: private */
    public String customerId;
    /* access modifiers changed from: private */
    public LatLng destinationLatLng;
    /* access modifiers changed from: private */
    public String distance;
    /* access modifiers changed from: private */
    public String driverId;
    /* access modifiers changed from: private */
    public DatabaseReference historyRideInfoDb;
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    private Button mPay;
    /* access modifiers changed from: private */
    public RatingBar mRatingBar;
    /* access modifiers changed from: private */
    public LatLng pickupLatLng;
    private List<Polyline> polylines;
    /* access modifiers changed from: private */
    public TextView rideDate;
    /* access modifiers changed from: private */
    public TextView rideDistance;
    /* access modifiers changed from: private */
    public String rideId;
    /* access modifiers changed from: private */
    public TextView rideLocation;
    /* access modifiers changed from: private */
    public Double ridePrice;
    /* access modifiers changed from: private */
    public String userDriverOrCustomer;
    /* access modifiers changed from: private */
    public ImageView userImage;
    /* access modifiers changed from: private */
    public TextView userName;
    /* access modifiers changed from: private */
    public TextView userPhone;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1102R.layout.activity_history_single);
        this.polylines = new ArrayList();
        this.rideId = getIntent().getExtras().getString("rideId");
        this.mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(C1102R.C1105id.map3);
        this.mMapFragment.getMapAsync(this);
        this.rideLocation = (TextView) findViewById(C1102R.C1105id.rideLocation);
        this.rideDistance = (TextView) findViewById(C1102R.C1105id.rideDistance);
        this.rideDate = (TextView) findViewById(C1102R.C1105id.rideDate);
        this.userName = (TextView) findViewById(C1102R.C1105id.userName);
        this.userPhone = (TextView) findViewById(C1102R.C1105id.userPhone);
        this.userImage = (ImageView) findViewById(C1102R.C1105id.userImage);
        this.mRatingBar = (RatingBar) findViewById(C1102R.C1105id.ratingBar);
        this.currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.historyRideInfoDb = FirebaseDatabase.getInstance().getReference().child("history").child(this.rideId);
        getRideInformation();
    }

    public Bitmap resizeBitmap(String drawableName, int width, int height) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(drawableName, "drawable", getPackageName())), width, height, false);
    }

    private void getRideInformation() {
        this.historyRideInfoDb.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getKey().equals("customer")) {
                            String unused = HistorySingleActivity.this.customerId = child.getValue().toString();
                            if (!HistorySingleActivity.this.customerId.equals(HistorySingleActivity.this.currentUserId)) {
                                String unused2 = HistorySingleActivity.this.userDriverOrCustomer = "Drivers";
                                HistorySingleActivity historySingleActivity = HistorySingleActivity.this;
                                historySingleActivity.getUserInformation("Customers", historySingleActivity.customerId);
                            }
                        }
                        if (child.getKey().equals("driver")) {
                            String unused3 = HistorySingleActivity.this.driverId = child.getValue().toString();
                            if (!HistorySingleActivity.this.driverId.equals(HistorySingleActivity.this.currentUserId)) {
                                String unused4 = HistorySingleActivity.this.userDriverOrCustomer = "Customers";
                                HistorySingleActivity historySingleActivity2 = HistorySingleActivity.this;
                                historySingleActivity2.getUserInformation("Drivers", historySingleActivity2.driverId);
                                HistorySingleActivity.this.displayCustomerRelatedObjects();
                            }
                        }
                        if (child.getKey().equals("timestamp")) {
                            HistorySingleActivity.this.rideDate.setText(HistorySingleActivity.this.getDate(Long.valueOf(child.getValue().toString())));
                        }
                        if (child.getKey().equals("rating")) {
                            HistorySingleActivity.this.mRatingBar.setRating((float) Integer.valueOf(child.getValue().toString()).intValue());
                        }
                        if (child.getKey().equals("distance")) {
                            String unused5 = HistorySingleActivity.this.distance = child.getValue().toString();
                            TextView access$1000 = HistorySingleActivity.this.rideDistance;
                            access$1000.setText(HistorySingleActivity.this.distance.substring(0, Math.min(HistorySingleActivity.this.distance.length(), 5)) + " km");
                            HistorySingleActivity historySingleActivity3 = HistorySingleActivity.this;
                            Double unused6 = historySingleActivity3.ridePrice = Double.valueOf(Double.valueOf(historySingleActivity3.distance).doubleValue() * 0.5d);
                        }
                        if (child.getKey().equals(FirebaseAnalytics.Param.DESTINATION)) {
                            HistorySingleActivity.this.rideLocation.setText(child.getValue().toString());
                        }
                        if (child.getKey().equals(FirebaseAnalytics.Param.LOCATION)) {
                            LatLng unused7 = HistorySingleActivity.this.pickupLatLng = new LatLng(Double.valueOf(child.child("from").child("lat").getValue().toString()).doubleValue(), Double.valueOf(child.child("from").child("lng").getValue().toString()).doubleValue());
                            LatLng unused8 = HistorySingleActivity.this.destinationLatLng = new LatLng(Double.valueOf(child.child("to").child("lat").getValue().toString()).doubleValue(), Double.valueOf(child.child("to").child("lng").getValue().toString()).doubleValue());
                            if (HistorySingleActivity.this.destinationLatLng != new LatLng(0.0d, 0.0d)) {
                                HistorySingleActivity.this.getRouteToMarker();
                            }
                        }
                    }
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void displayCustomerRelatedObjects() {
        this.mRatingBar.setVisibility(0);
        this.mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                HistorySingleActivity.this.historyRideInfoDb.child("rating").setValue(Float.valueOf(rating));
                FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(HistorySingleActivity.this.driverId).child("rating").child(HistorySingleActivity.this.rideId).setValue(Float.valueOf(rating));
            }
        });
    }

    /* access modifiers changed from: private */
    public void getUserInformation(String otherUserDriverOrCustomer, String otherUserId) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(otherUserDriverOrCustomer).child(otherUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Object> map = (Map) dataSnapshot.getValue();
                    if (map.get(AppMeasurementSdk.ConditionalUserProperty.NAME) != null) {
                        HistorySingleActivity.this.userName.setText(map.get(AppMeasurementSdk.ConditionalUserProperty.NAME).toString());
                    }
                    if (map.get("phone") != null) {
                        HistorySingleActivity.this.userPhone.setText(map.get("phone").toString());
                    }
                    if (map.get("profileImageUrl") != null) {
                        Glide.with((Context) HistorySingleActivity.this.getApplication()).load(map.get("profileImageUrl").toString()).into(HistorySingleActivity.this.userImage);
                    }
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

    /* access modifiers changed from: private */
    public void getRouteToMarker() {
        new Routing.Builder().travelMode(AbstractRouting.TravelMode.DRIVING).withListener(this).alternativeRoutes(false).waypoints(this.pickupLatLng, this.destinationLatLng).build().execute(new Void[0]);
    }

    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    public void onRoutingFailure(RouteException e) {
        if (e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), 1).show();
            return;
        }
        Toast.makeText(this, "Something went wrong, Try again", 0).show();
    }

    public void onRoutingStart() {
    }

    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(this.pickupLatLng);
        builder.include(this.destinationLatLng);
        this.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), (int) (((double) getResources().getDisplayMetrics().widthPixels) * 0.2d)));
        this.mMap.addMarker(new MarkerOptions().position(this.pickupLatLng).title("pickup location").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_pickup", 72, 64))));
        this.mMap.addMarker(new MarkerOptions().position(this.destinationLatLng).title(FirebaseAnalytics.Param.DESTINATION));
        if (this.polylines.size() > 0) {
            for (Polyline poly : this.polylines) {
                poly.remove();
            }
        }
        this.polylines = new ArrayList();
        for (int i = 0; i < route.size(); i++) {
            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[i % COLORS.length]));
            polyOptions.width((float) ((i * 3) + 10));
            polyOptions.addAll(route.get(i).getPoints());
            this.polylines.add(this.mMap.addPolyline(polyOptions));
            Context applicationContext = getApplicationContext();
            Toast.makeText(applicationContext, "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), 0).show();
        }
    }

    public void onRoutingCancelled() {
    }
}
