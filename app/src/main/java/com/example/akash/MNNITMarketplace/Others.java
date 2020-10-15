package com.example.akash.MNNITMarketplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.zip.Inflater;


public class Others extends Fragment {

    RecyclerView mothers;
    DatabaseReference mdatabase;
    public Others(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_others,container,false);
        mothers = V.findViewById(R.id.other_view_id);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Sell").child("Others");
        mothers.setHasFixedSize(true);
        mothers.setLayoutManager(new LinearLayoutManager(getActivity()));
        return V;
    }

    @Override
    public void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<b_ooks> options = new FirebaseRecyclerOptions.Builder<b_ooks>().setQuery(mdatabase,b_ooks.class).build();
        FirebaseRecyclerAdapter<b_ooks,othersviewholder> recyclerAdapter = new FirebaseRecyclerAdapter<b_ooks, othersviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final othersviewholder holder, final int position, @NonNull b_ooks model) {
                final String UserIds = getRef(position).getKey();
                mdatabase.child(UserIds).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String title = dataSnapshot.child("Title").getValue().toString();
                        String price = dataSnapshot.child("Price").getValue().toString();
                        String img_url  = dataSnapshot.child("Image").getValue().toString();
                        holder.settitle(title);
                        holder.setprice(price);
                        Glide.with(getContext()).load(img_url).apply(new RequestOptions().override(128,120)).into(holder.imgview);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Id = getRef(position).getKey();
                        Intent intent = new Intent(getContext(),Item_activity.class);
                        intent.putExtra("item_id",Id);
                        intent.putExtra("item_type","Others");
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public othersviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new othersviewholder(inflater.inflate(R.layout.items_list_layout,viewGroup,false));
            }
        };
        mothers.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();
    }
    public static class othersviewholder extends RecyclerView.ViewHolder{
        View mview ;
        ImageView imgview;
        public othersviewholder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
            imgview = itemView.findViewById(R.id.item_img);
        }

        public void settitle(String title) {
            TextView mtitle = mview.findViewById(R.id.item_title);
            mtitle.setText(title);
        }

        public void setprice(String price) {
            TextView mprice = mview.findViewById(R.id.item_price);
            mprice.setText(price);
        }
    }
}




