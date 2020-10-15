package com.example.akash.MNNITMarketplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Electronics extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private RecyclerView melectronics;
    DatabaseReference mdatabaseref;

    public Electronics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_electronics,container,false);
        melectronics = V.findViewById(R.id.electronics_list);
        melectronics.setHasFixedSize(true);
        melectronics.setLayoutManager(new LinearLayoutManager(getActivity()));
        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("Sell").child("Electronics");
        return V;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<b_ooks> options = new FirebaseRecyclerOptions.Builder<b_ooks>().setQuery(mdatabaseref,b_ooks.class).build();
        FirebaseRecyclerAdapter<b_ooks,electronicsviewholder> recyclerAdapter = new FirebaseRecyclerAdapter<b_ooks, electronicsviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final electronicsviewholder holder, final int position, @NonNull b_ooks model) {
                final String UserIds = getRef(position).getKey();
                mdatabaseref.child(UserIds).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String title = dataSnapshot.child("Title").getValue().toString();
                        String price = dataSnapshot.child("Price").getValue().toString();
                        String imageurl = dataSnapshot.child("Image").getValue().toString();
                        holder.setTitle(title);
                        holder.setPrice(price);
                        Glide.with(getContext()).load(imageurl).apply(new RequestOptions().override(128,120)).into(holder.imgview);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String item_id = getRef(position).getKey();
                        Intent intent = new Intent(getContext(),Item_activity.class);
                        intent.putExtra("item_id",item_id);
                        intent.putExtra("item_type","Electronics");
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public electronicsviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new electronicsviewholder(inflater.inflate(R.layout.items_list_layout,viewGroup,false));
            }
        } ;
        melectronics.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();
    }
    public static class electronicsviewholder extends RecyclerView.ViewHolder{

        View mview;
        ImageView imgview;
        public electronicsviewholder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
            imgview = itemView.findViewById(R.id.item_img);
        }
        public void setTitle(String title){
            TextView mtitleview = mview.findViewById(R.id.item_title);
            mtitleview.setText(title);
        }
        public void setPrice(String price){
            TextView mpriceview = mview.findViewById(R.id.item_price);
            mpriceview.setText(price);
        }
    }
}

