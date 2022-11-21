package com.friday.etsfinalone;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.p004ui.AppBarConfiguration;
import androidx.navigation.p004ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;

public class CustomerMapActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1102R.layout.activity_customer_map);
        setSupportActionBar((Toolbar) findViewById(C1102R.C1105id.toolbar));
        this.mAppBarConfiguration = new AppBarConfiguration.Builder(C1102R.C1105id.user_nav_home, C1102R.C1105id.user_nav_gallery, C1102R.C1105id.user_nav_tools, C1102R.C1105id.user_Logout).setDrawerLayout((DrawerLayout) findViewById(C1102R.C1105id.user_drawer_layout)).build();
        NavController navController = Navigation.findNavController(this, C1102R.C1105id.user_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) this, navController, this.mAppBarConfiguration);
        NavigationUI.setupWithNavController((NavigationView) findViewById(C1102R.C1105id.user_nav_view), navController);
    }

    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, C1102R.C1105id.user_nav_host_fragment), this.mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}
