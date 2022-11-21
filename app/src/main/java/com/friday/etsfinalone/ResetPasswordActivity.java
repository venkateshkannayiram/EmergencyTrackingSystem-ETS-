package com.friday.etsfinalone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private Button btnBack;
    private Button btnResetPassword;
    /* access modifiers changed from: private */
    public EditText edtEmail;
    /* access modifiers changed from: private */
    public FirebaseAuth mAuth;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1102R.layout.activity_reset_password);
        this.edtEmail = (EditText) findViewById(C1102R.C1105id.edt_reset_email);
        this.btnResetPassword = (Button) findViewById(C1102R.C1105id.btn_reset_password);
        this.btnBack = (Button) findViewById(C1102R.C1105id.btn_back);
        this.mAuth = FirebaseAuth.getInstance();
        this.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = ResetPasswordActivity.this.edtEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ResetPasswordActivity.this.getApplicationContext(), "Enter your email!", 0).show();
                } else {
                    ResetPasswordActivity.this.mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "Check email to reset your password!", 0).show();
                            } else {
                                Toast.makeText(ResetPasswordActivity.this, "Fail to send reset password email!", 0).show();
                            }
                        }
                    });
                }
            }
        });
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ResetPasswordActivity resetPasswordActivity = ResetPasswordActivity.this;
                resetPasswordActivity.startActivity(new Intent(resetPasswordActivity.getApplicationContext(), ResponderLoginActivity.class).setFlags(67108864));
            }
        });
    }
}
