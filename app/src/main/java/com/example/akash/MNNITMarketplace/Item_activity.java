package com.example.akash.MNNITMarketplace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.akash.MNNITMarketplace.idea.Cart_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Item_activity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private String imageURL;
    private ImageView imageView;
    private TextView item_name;
    private Button buy_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_activity);

        final android.support.v7.widget.Toolbar toolbar  =  findViewById(R.id.item_activity_toolbar);
        setActionBar(toolbar);

        final String item_id = getIntent().getExtras().getString("item_id");
        String item_type = getIntent().getExtras().getString("item_type");

        imageView = findViewById(R.id.image);
        item_name = findViewById(R.id.item_name);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Sell").child(item_type).child(item_id);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               imageURL = dataSnapshot.child("Image").getValue().toString();
               item_name.setText(dataSnapshot.child("Title").getValue().toString());
               Glide.with(getApplicationContext()).load(imageURL).apply(new RequestOptions(). override(330,220)).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Database error",Toast.LENGTH_SHORT).show();
            }
        });

        buy_button = findViewById(R.id.buy_button);
        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String seller_id = dataSnapshot.child("User_Id").getValue().toString();
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                        if(seller_id.equals(uid))
                            Toast.makeText(getApplicationContext(),"You can't buy your own item.",Toast.LENGTH_LONG).show();
                        else{
                            Intent intent = new Intent(getApplicationContext(),Cart_activity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private void setActionBar(android.support.v7.widget.Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
