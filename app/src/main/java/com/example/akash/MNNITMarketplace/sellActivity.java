package com.example.akash.MNNITMarketplace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class sellActivity extends AppCompatActivity {

    private Spinner categoryspin;
    private EditText Titlesell,Descriptionsell,Pricesell;
    private Button Proceed;
    private EditText Location_sell;
    private DatabaseReference mDatabase_Sell;
    private Button add_img;
    private ProgressDialog mprogressBar ;
    private String uid;
    private Uri resultUri;
    private StorageReference mstoragereference;
    boolean clicked = false;
   // private String downloadUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        final Toolbar mtoolbar = (Toolbar) findViewById(R.id.sell_page_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Sell Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Titlesell = (EditText) findViewById(R.id.sell_item_title_input);
        Descriptionsell = (EditText) findViewById(R.id.Description_sell);
        categoryspin = (Spinner) findViewById(R.id.spinner_sell_category);
        Pricesell = (EditText) findViewById(R.id.price_sell);
        Proceed = (Button) findViewById(R.id.Proceed_Sell);
        Location_sell = (EditText) findViewById(R.id.Location_sell);
        add_img = (Button) findViewById(R.id.add_img);

        mstoragereference = FirebaseStorage.getInstance().getReference();
        mprogressBar = new ProgressDialog(this);


        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .start(sellActivity.this);
               // clicked=true;
            }
        });


        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked) {
                    final String title_sell = Titlesell.getText().toString();
                    final String descr_sell = Descriptionsell.getText().toString();
                    final String ctg_spin = categoryspin.getSelectedItem().toString();
                    final String price_sell = Pricesell.getText().toString();
                    final String loc_sell = Location_sell.getText().toString();

                    if (!TextUtils.isEmpty(title_sell) && !TextUtils.isEmpty(descr_sell) && !TextUtils.isEmpty(ctg_spin) && !TextUtils.isEmpty(price_sell) && !TextUtils.isEmpty(loc_sell)) {
                        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                        uid = current_user.getUid();
                        mprogressBar.setTitle("Placing your item into market..");
                        mprogressBar.setMessage("Please Wait....");
                        mprogressBar.show();
                        mDatabase_Sell = FirebaseDatabase.getInstance().getReference().child("Sell").child(ctg_spin);//.child(uid);
                        DatabaseReference UserImg_sell_push = mDatabase_Sell.push();
                        final String push_id = UserImg_sell_push.getKey();

                        final StorageReference posts_image_ref = mstoragereference.child("Posts").child(uid).child(push_id + ".jpg");

                        posts_image_ref.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                posts_image_ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                      final String  downloadUri = uri.toString();
                                        HashMap<String, String> Booksuser = new HashMap<>();
                                        Booksuser.put("Title", title_sell);
                                        Booksuser.put("Description", descr_sell);
                                        Booksuser.put("Price", price_sell);
                                        Booksuser.put("Image",downloadUri);
                                        Booksuser.put("Location", loc_sell);
                                        Booksuser.put("User_Id",uid);

                                        mDatabase_Sell.child(push_id).setValue(Booksuser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    mprogressBar.dismiss();
                                                    Toast.makeText(sellActivity.this, "Item has been added", Toast.LENGTH_LONG).show();
                                                    Intent sell_back_intent = new Intent(sellActivity.this, MainActivity.class);
                                                    startActivity(sell_back_intent);
                                                } else {
                                                    mprogressBar.dismiss();
                                                    Toast.makeText(sellActivity.this, "Error Occured!!!!!", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                });


                            }
                        });


                    } else {
                        Toast.makeText(sellActivity.this, "Please fill all the entries", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(sellActivity.this, "Please add image also..", Toast.LENGTH_LONG).show();
                }
            }


        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                clicked = true;
            }
        }
    }


}