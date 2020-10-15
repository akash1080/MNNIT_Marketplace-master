package com.example.akash.MNNITMarketplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button mRegBtn ;
    private Button mloginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar SToolbar = (Toolbar) findViewById(R.id.start_page_toolbar);
        setSupportActionBar(SToolbar);
        getSupportActionBar().setTitle("    MNNIT MARKETPLACE");

        mRegBtn = (Button) findViewById(R.id.startregbtn);
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg_intent = new Intent(StartActivity.this,registerActivity.class);
                startActivity(reg_intent);
            }
        });
        mloginBtn = (Button) findViewById(R.id.login_reg_btn);
        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_intent = new Intent(StartActivity.this,login_activity.class);
                startActivity(login_intent);
            }
        });
    }
}
