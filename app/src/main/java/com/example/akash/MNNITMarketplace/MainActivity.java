package com.example.akash.MNNITMarketplace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.TabLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final String MY_TAG = "the custom message";
    private FirebaseAuth mAuth;
    private ProgressDialog mLogoutprogress ;
    private ViewPager mViewPager;
    private com.example.akash.MNNITMarketplace.SectionPagerAdapter msectionAdapter;
    private TabLayout mtablayout;
    private FloatingActionButton mfab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(MY_TAG,"On Create");
        mAuth = FirebaseAuth.getInstance();
        mLogoutprogress = new ProgressDialog(this);
        android.support.v7.widget.Toolbar mToolbar =(android.support.v7.widget.Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("MNNIT Marketplace");

        mViewPager = (ViewPager) findViewById(R.id.tabpager);
        msectionAdapter = new com.example.akash.MNNITMarketplace.SectionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(msectionAdapter);

        mtablayout = (TabLayout) findViewById(R.id.mainTab);
        mtablayout.setupWithViewPager(mViewPager);

        mfab = (FloatingActionButton) findViewById(R.id.fab);
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sellIntent = new Intent(MainActivity.this, com.example.akash.MNNITMarketplace.sellActivity.class);
                startActivity(sellIntent);
            }
        });

    }
    public void onStart() {
        super.onStart();
        Log.i(MY_TAG, "onStart");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent startIntent = new Intent(MainActivity.this, com.example.akash.MNNITMarketplace.StartActivity.class);
            startActivity(startIntent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.main_log_btn){
            mLogoutprogress.setTitle("Logging Out");
            mLogoutprogress.setMessage("Please Wait");
            mLogoutprogress.setCanceledOnTouchOutside(false);
            mLogoutprogress.show();
            FirebaseAuth.getInstance().signOut();
            Intent startIntent = new Intent(MainActivity.this, com.example.akash.MNNITMarketplace.StartActivity.class);
            startActivity(startIntent);
            finish();
        }
        if(item.getItemId()==R.id.my_account_btn){
            Intent myaccount = new Intent(MainActivity.this,myAccount.class);
            startActivity(myaccount);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(MY_TAG, "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(MY_TAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(MY_TAG, "onStop");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(MY_TAG, "onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLogoutprogress.dismiss();
        Log.i(MY_TAG, "onDestroy");
    }



}
