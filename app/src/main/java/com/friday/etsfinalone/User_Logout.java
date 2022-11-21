package com.friday.etsfinalone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class User_Logout extends AppCompatActivity {
    Button btnCancel;
    Button btnSignOut;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1102R.layout.activity_user__logout);
        this.btnCancel = (Button) findViewById(C1102R.C1105id.cancel);
        this.btnSignOut = (Button) findViewById(C1102R.C1105id.sign_out);
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User_Logout user_Logout = User_Logout.this;
                user_Logout.startActivity(new Intent(user_Logout.getApplicationContext(), CustomerMapActivity.class).setFlags(67108864));
            }
        });
        this.btnSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                User_Logout.this.startActivity(new Intent(User_Logout.this, MainActivity.class));
                User_Logout.this.finish();
            }
        });
    }
}
