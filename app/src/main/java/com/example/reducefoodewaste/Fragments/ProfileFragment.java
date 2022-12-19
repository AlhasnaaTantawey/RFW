package com.example.reducefoodewaste.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.reducefoodewaste.Adapters.RecyclerViewAdapterMainPageActivity;
import com.example.reducefoodewaste.Models.MealModel;
import com.example.reducefoodewaste.Models.UserModel;
import com.example.reducefoodewaste.Ui.MainPageActivity;
import com.example.reducefoodewastebasic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ProfileFragment extends Fragment {
    private static final String USERS = "user";
    EditText name, mail,password,confirmPassword;
    ImageView picture;
    Button save;
    Uri uriImage=null;
    public static final int CHOOSE_IMAGE = 2;
    StorageReference storageReference;
    private String muri;
    FirebaseFirestore db;
    FirebaseStorage storage;
    ArrayList<MealModel> meals;
    ArrayList<String> mealsid;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        db = FirebaseFirestore.getInstance();
        name = v.findViewById(R.id.profileFragment_edittext_username);
        mail = v.findViewById(R.id.profileFragment_edittext_email);
        password=v.findViewById(R.id.profileFragment_edittext_password);
        confirmPassword=v.findViewById(R.id.profileFragment_edittext_confirmPassword);
        picture = v.findViewById(R.id.profileFragment_image_photo);
        save = v.findViewById(R.id.save);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        //fetch data from firebase
        FirebaseFirestore.getInstance().collection("users")
                .document(getcurrentuserid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserModel user=  documentSnapshot.toObject(UserModel.class);
                assert user != null;
                name.setText(user.getName());
                mail.setText(user.getGmail());
                password.setText(documentSnapshot.getString("password"));
                confirmPassword.setText(documentSnapshot.getString("confirmpassword"));
                if (!Objects.equals(user.getImageUrl(), "")){
                    Picasso.get().load(user.getImageUrl()).into(picture);
                    Glide.with(getContext())
                            .load(user.getImageUrl())
                            .placeholder(R.drawable.progress_animation)
                            .into(picture);
                }
            }

        });

        meals = new ArrayList<>();
        mealsid = new ArrayList<>();

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("MealModel")
                .orderBy("mealName").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    MealModel mealModel1 = snapshot.toObject(MealModel.class);
                    meals.add(mealModel1);
                    assert mealModel1 != null;
                    if (Objects.equals(getcurrentuserid(), mealModel1.getMealid())){
                        mealsid.add(mealModel1.getdocid());
                    }

                }
                Log.i("listsizee",String.valueOf(mealsid.size()));

            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });
        return v;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == CHOOSE_IMAGE && resultCode == Activity. RESULT_OK & data != null & data.getData() != null) {
            uriImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uriImage);
                picture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void  pickImageFromGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "select picture"), CHOOSE_IMAGE);
    }

    private void uploadImage() {
        if(uriImage != null)
        { final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.setMax(100);
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(uriImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    muri = uri.toString();
                                    updateImage(getcurrentuserid());
                                }
                            });

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    public String getcurrentuserid() {
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String currentuserid = "";
        if (currentuser != null) {
            currentuserid = currentuser.getUid();
        }
        return currentuserid;

    }
//update on firebase

    public void updateImage( String docid) {
        db.collection("users")
                .document(docid)
                .update("imageUrl",muri)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(requireActivity(),"photo updated successfully",Toast.LENGTH_LONG).show();
                     /*
                        for (String k : mealsid ){
                            updatemeal(k);
                        }
                        QfKb7VNOyubJoGC3ruVpzXnpQ4Q2
                      */
                        requireActivity().finish();
                        startActivity(new Intent(requireActivity(), MainPageActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    public void updatemeal(String docid){

        db.collection("MealModel")
                .document(docid)
                .update("userImage",muri)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Log.i("mealupdated","meal");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

}