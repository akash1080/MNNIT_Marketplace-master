package com.example.akash.MNNITMarketplace;


import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Books extends Fragment {

    private RecyclerView mbookslist;
    DatabaseReference mdatabasereference;
    String title;
    String price;

    public Books() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_books, container, false);
        mbookslist = V.findViewById(R.id.books_list);
        mbookslist.setHasFixedSize(true);
        mbookslist.setLayoutManager(new LinearLayoutManager(getActivity()));
        mdatabasereference = FirebaseDatabase.getInstance().getReference().child("Sell").child("Books");
        return V;
        }
       @Override
       public void onStart()
        {
            super.onStart();
            FirebaseRecyclerOptions<b_ooks> options = new FirebaseRecyclerOptions.Builder<b_ooks>()
                                                         .setQuery(mdatabasereference,b_ooks.class)
                                                         .build();
          FirebaseRecyclerAdapter<b_ooks,booksviewholder> recyclerAdapter = new FirebaseRecyclerAdapter<b_ooks, booksviewholder>(options) {
              @Override
              protected void onBindViewHolder(@NonNull final booksviewholder holder, final int position, @NonNull b_ooks model) {
                  final String UserIds = getRef(position).getKey();

                  mdatabasereference.child(UserIds).addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          title = dataSnapshot.child("Title").getValue().toString();
                          price = dataSnapshot.child("Price").getValue().toString();
                          String ImageUrl = dataSnapshot.child("Image").getValue().toString();
                          holder.setTitle(title);
                          holder.setPrice(price);
                          Glide.with(getContext()).load(ImageUrl).apply(new RequestOptions(). override(128,120)).into(holder.mimage);
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String item_id = getRef(position).getKey();
                            Intent intent = new Intent(v.getContext(),Item_activity.class);
                            intent.putExtra("item_id",item_id);
                            intent.putExtra("item_type","Books");
                            startActivity(intent);
                        }
                    });
              }
              @NonNull
              @Override
              public booksviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                  LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                  return new booksviewholder(inflater.inflate(R.layout.items_list_layout,viewGroup,false));
              }
          };
          mbookslist.setAdapter(recyclerAdapter);
            recyclerAdapter.startListening();
        }
        public static class booksviewholder extends RecyclerView.ViewHolder{
            View mview;
           ImageView mimage;
            public booksviewholder(@NonNull View itemView) {
                super(itemView);
                mimage = itemView.findViewById(R.id.item_img);
                mview = itemView;

            }

            public void setTitle(String title) {
                TextView mtextview = mview.findViewById(R.id.item_title);
                mtextview.setText(title);
            }

            public void setPrice(String price) {
                TextView mpriceview =  mview.findViewById(R.id.item_price);
                mpriceview.setText(price);
            }
        }
}

