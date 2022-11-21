package com.friday.etsfinalone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Res_Logout extends AppCompatActivity {
    Button btnCancel1;
    Button btnSignOut1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1102R.layout.activity_res__logout);
        this.btnCancel1 = (Button) findViewById(C1102R.C1105id.cancel1);
        this.btnSignOut1 = (Button) findViewById(C1102R.C1105id.sign_out1);
        this.btnCancel1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Res_Logout res_Logout = Res_Logout.this;
                res_Logout.startActivity(new Intent(res_Logout.getApplicationContext(), ResponderMapActivity.class).setFlags(67108864));
            }
        });
        this.btnSignOut1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Res_Logout.this.startActivity(new Intent(Res_Logout.this, MainActivity.class));
                Res_Logout.this.finish();
            }
        });
    }
}
