package com.example.akash.MNNITMarketplace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_activity extends AppCompatActivity {
    private EditText login_email;
    private EditText login_password;
    private Button login_btn;

    private ProgressDialog mLoginprogress;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.login_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoginprogress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        login_email = (EditText) findViewById(R.id.loginemail);
        login_password = (EditText) findViewById(R.id.loginpassword);
        login_btn = (Button) findViewById(R.id.loginbtn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = login_email.getText().toString();
                String Password = login_password.getText().toString();

                if(!TextUtils.isEmpty(Email)&&!TextUtils.isEmpty(Password)){
                    mLoginprogress.setTitle("Logging In");
                    mLoginprogress.setMessage("Please Wait");
                    mLoginprogress.setCanceledOnTouchOutside(false);
                    mLoginprogress.show();
                    login_user(Email,Password);
                }
                else{
                    Toast.makeText(login_activity.this,"Please fill all the entries",Toast.LENGTH_LONG).show();
                }
            }


        });
    }
    private void login_user(String login_email, String login_password) {

        mAuth.signInWithEmailAndPassword(login_email, login_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent mainIntent = new Intent(login_activity.this,MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                            mLoginprogress.dismiss();
                        } else {
                            mLoginprogress.hide();
                            Toast.makeText(login_activity.this,"Wrong E-mail Id or password!!",Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
    }

}
