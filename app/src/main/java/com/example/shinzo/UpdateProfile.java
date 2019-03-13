package com.example.shinzo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UpdateProfile extends AppCompatActivity {


    private EditText newUserName, newUserEmail, newUserAge,  newUserHeight,  newUserWeight,  newUserec1,  newUserec2,  newUserec3;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView updateProfilePic;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                updateProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        newUserName = findViewById(R.id.etNameUpdate);
        newUserEmail = findViewById(R.id.etEmailUpdate);
        newUserAge = findViewById(R.id.etAgeUpdate);
        save = findViewById(R.id.btnSave);
        updateProfilePic = findViewById(R.id.ivProfileUpdate);
        newUserHeight = findViewById(R.id.etHeightUpdate);
        newUserWeight = findViewById(R.id.etWeightUpdate);
        newUserec1 = findViewById(R.id.etec1Update);
        newUserec2 = findViewById(R.id.etec2Update);
        newUserec3 = findViewById(R.id.etec3Update);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                newUserName.setText(userProfile.getUserName());
                newUserAge.setText(userProfile.getUserAge());
                newUserEmail.setText(userProfile.getUserEmail());
                newUserHeight.setText(userProfile.getUserHeight());
                newUserWeight.setText(userProfile.getUserWeight());
                newUserec1.setText(userProfile.getUserec1());
                newUserec2.setText(userProfile.getUserec2());
                newUserec3.setText(userProfile.getUserec3());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(updateProfilePic);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newUserName.getText().toString();
                String age = newUserAge.getText().toString();
                String email = newUserEmail.getText().toString();
                String weight = newUserWeight.getText().toString();
                String height = newUserHeight.getText().toString();
                String ec1 = newUserec1.getText().toString();
                String ec2 = newUserec2.getText().toString();
                String ec3 = newUserec3.getText().toString();


                UserProfile userProfile = new UserProfile(age, email, name, weight, height, ec1, ec2, ec3);

                databaseReference.setValue(userProfile);

                StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
                UploadTask uploadTask = imageReference.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(UpdateProfile.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                    }
                });

                finish();
            }
        });

        updateProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/jpg");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
