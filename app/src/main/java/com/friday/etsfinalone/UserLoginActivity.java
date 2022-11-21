package com.friday.etsfinalone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.friday.etsfinalone.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import dmax.dialog.SpotsDialog;
import p009uk.p010co.chrisjenx.calligraphy.CalligraphyConfig;
import p009uk.p010co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserLoginActivity extends AppCompatActivity {
    private static final int PERMISSION = 1000;
    FirebaseAuth auth;
    private Button btnBack;
    Button btnForgot;
    Button btnRegister;
    Button btnSignIn;

    /* renamed from: db */
    FirebaseDatabase f151db;
    /* access modifiers changed from: private */
    public FirebaseAuth mAuth;
    RelativeLayout rootLayout;

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context newbase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newbase));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Arkhip_font.ttf").setFontAttrId(C1102R.attr.fontPath).build());
        setContentView((int) C1102R.layout.activity_user_login);
        this.auth = FirebaseAuth.getInstance();
        this.f151db = FirebaseDatabase.getInstance();
        ((ImageButton) findViewById(C1102R.C1105id.back_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UserLoginActivity userLoginActivity = UserLoginActivity.this;
                userLoginActivity.startActivity(new Intent(userLoginActivity.getApplicationContext(), MainActivity.class).setFlags(67108864));
            }
        });
        this.btnRegister = (Button) findViewById(C1102R.C1105id.btnRegister);
        this.btnSignIn = (Button) findViewById(C1102R.C1105id.btnSignIn);
        this.btnForgot = (Button) findViewById(C1102R.C1105id.btn_forgot_password_Customer);
        this.rootLayout = (RelativeLayout) findViewById(C1102R.C1105id.rootLayout);
        this.mAuth = FirebaseAuth.getInstance();
        startService(new Intent(this, onAppKilled.class));
        this.btnForgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserLoginActivity.this.startActivity(new Intent(UserLoginActivity.this, ResetPasswordActivity.class));
                UserLoginActivity.this.finish();
            }
        });
        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserLoginActivity.this.showRegisterDiaog();
            }
        });
        this.btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserLoginActivity.this.showLoginDialog();
            }
        });
    }

    /* access modifiers changed from: private */
    public void showLoginDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGN IN");
        dialog.setMessage("Please use email to sign in");
        View login_layout = LayoutInflater.from(this).inflate(C1102R.layout.layout_signin, (ViewGroup) null);
        final MaterialEditText edtEmail = (MaterialEditText) login_layout.findViewById(C1102R.C1105id.edtEmail);
        final MaterialEditText edtPassword = (MaterialEditText) login_layout.findViewById(C1102R.C1105id.edtPassword);
        dialog.setView(login_layout);
        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                UserLoginActivity.this.btnSignIn.setEnabled(false);
                if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                    Snackbar.make((View) UserLoginActivity.this.rootLayout, (CharSequence) "Please enter email address", -1).show();
                } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    Snackbar.make((View) UserLoginActivity.this.rootLayout, (CharSequence) "Please enter password", -1).show();
                } else if (edtPassword.getText().toString().length() < 6) {
                    Snackbar.make((View) UserLoginActivity.this.rootLayout, (CharSequence) "Password too short !!!", -1).show();
                } else {
                    final SpotsDialog waitingDialog = new SpotsDialog(UserLoginActivity.this);
                    waitingDialog.show();
                    UserLoginActivity.this.auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        public void onSuccess(AuthResult authResult) {
                            waitingDialog.dismiss();
                            UserLoginActivity.this.startActivity(new Intent(UserLoginActivity.this, CustomerMapActivity.class));
                            UserLoginActivity.this.finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        public void onFailure(Exception e) {
                            waitingDialog.dismiss();
                            RelativeLayout relativeLayout = UserLoginActivity.this.rootLayout;
                            Snackbar.make((View) relativeLayout, (CharSequence) "Failed" + e.getMessage(), -1).show();
                            UserLoginActivity.this.btnSignIn.setEnabled(true);
                        }
                    });
                }
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    /* access modifiers changed from: private */
    public void showRegisterDiaog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER");
        dialog.setMessage("Please use email to register");
        View register_layout = LayoutInflater.from(this).inflate(C1102R.layout.layout_register, (ViewGroup) null);
        final MaterialEditText edtEmail = (MaterialEditText) register_layout.findViewById(C1102R.C1105id.edtEmail);
        final MaterialEditText edtPassword = (MaterialEditText) register_layout.findViewById(C1102R.C1105id.edtPassword);
        dialog.setView(register_layout);
        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                    Snackbar.make((View) UserLoginActivity.this.rootLayout, (CharSequence) "Please enter email address", -1).show();
                } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    Snackbar.make((View) UserLoginActivity.this.rootLayout, (CharSequence) "Please enter password", -1).show();
                } else if (edtPassword.getText().toString().length() < 6) {
                    Snackbar.make((View) UserLoginActivity.this.rootLayout, (CharSequence) "Password too short !!!", -1).show();
                } else {
                    UserLoginActivity.this.auth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        public void onSuccess(AuthResult authResult) {
                            User driver = new User();
                            driver.setEmail(edtEmail.getText().toString());
                            driver.setPassword(edtPassword.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(UserLoginActivity.this.mAuth.getCurrentUser().getUid()).child("SignIn_Details").setValue(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
                                public void onSuccess(Void aVoid) {
                                    Snackbar.make((View) UserLoginActivity.this.rootLayout, (CharSequence) "Register Successfully !!!", -1).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                public void onFailure(Exception e) {
                                    RelativeLayout relativeLayout = UserLoginActivity.this.rootLayout;
                                    Snackbar.make((View) relativeLayout, (CharSequence) "Failed !!!" + e.getMessage(), -1).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        public void onFailure(Exception e) {
                            RelativeLayout relativeLayout = UserLoginActivity.this.rootLayout;
                            Snackbar.make((View) relativeLayout, (CharSequence) "Failed !!!" + e.getMessage(), -1).show();
                        }
                    });
                }
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
}
