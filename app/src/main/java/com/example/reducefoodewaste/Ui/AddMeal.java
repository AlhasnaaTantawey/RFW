package com.example.reducefoodewaste.Ui;

import static com.facebook.login.widget.ProfilePictureView.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reducefoodewaste.Fragments.HomeFragment;
import com.example.reducefoodewaste.Models.MealModel;
import com.example.reducefoodewaste.Models.UserModel;
import com.example.reducefoodewaste.Maps.CurrentLocationMap;
import com.example.reducefoodewaste.Retrofit.Client;
import com.example.reducefoodewaste.Retrofit.DetectFood;
import com.example.reducefoodewaste.Retrofit.EndPoint;
import com.example.reducefoodewastebasic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.squareup.okhttp.MediaType;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


//import com.squareup.okhttp.RequestBody;

//import okhttp3.MultipartBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddMeal extends AppCompatActivity {
 String select;
    private  int duration ;
    EditText timerDuration ;
    private boolean timerRunning = false;
    TextView hour ,min , sec ;
   public static  final String SELECTEDITEM="tag";
    Button mPickDateButton,mPickDateButton2,button,addMeal,inceamentbtn,decreamentbtn;
    TextView mShowSelectedDateText,mShowSelectedDateText2, number,foodType,foodPicture;
    ImageView image;
    public static final int CHOOSE_IMAGE = 2;
    String uriImage= "";
    EditText location,foodTitle,foodDetails;
    FirebaseFirestore db;
    private String muri;
    int counter=0;
    String photo,title,details,loc,numberValue,username,userimage, userId;
    FirebaseStorage storage;
    StorageReference storageReference;
    SharedPreferences sharpreferences;
    SharedPreferences login_sharpreferences;
    UserModel muser;
    DetectFood foodTypes;
     Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        sharpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        login_sharpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        userimage = "";
        getuser ();
        mPickDateButton = findViewById(R.id.pick_date_button);
        foodType=findViewById(R.id.sharefood_textview_foodType);
        mShowSelectedDateText = findViewById(R.id.show_selected_date);
        mPickDateButton2 = findViewById(R.id.pick_date_button2);
        mShowSelectedDateText2 = findViewById(R.id.show_selected_date2);
        image = findViewById(R.id.sharefood_imageView);
        timerDuration = findViewById(R.id.timer_duration);
        hour = findViewById(R.id.hour);
        min = findViewById(R.id.min);
        sec= findViewById(R.id.sec);
        foodPicture=findViewById(R.id.sharefood_textView_chooseFoodPicture);
        location=findViewById(R.id.sharefood_editText_location);
        foodTitle=findViewById(R.id.sharefood_editText_food_tile);
        foodDetails=findViewById(R.id.sharefood_editText_food_details);
        inceamentbtn=findViewById(R.id.sharefood_button_inceament);
        decreamentbtn=findViewById(R.id.sharefood_button_decreament);
        number=findViewById(R.id.sharefood_textViewt_number);
        final TextView hour = findViewById(R.id.hour);
        final TextView min = findViewById(R.id.min);
        final TextView sec= findViewById(R.id.sec);
        spinner=findViewById(R.id.type_spinner);


        // now create instance of the material date picker
        // builder make sure to add the "datePicker" which
        // is normal material date picker which is the first
        // type of the date picker in material design date
        // picker
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        // now define the properties of the
        // materialDateBuilder that is title text as SELECT A DATE
        materialDateBuilder.setTitleText("SELECT A DATE");
        // now create the instance of the material date
        // picker
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        // handle select date button which opens the
        // material design date picker
        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // getSupportFragmentManager() to
                        // interact with the fragments
                        // associated with the material design
                        // date picker tag is to get any error
                        // in logcat
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });
        // now handle the positive button click from the
        // material design date picker
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        mShowSelectedDateText.setText( materialDatePicker.getHeaderText());
                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });

        MaterialDatePicker.Builder materialDateBuilder2 = MaterialDatePicker.Builder.datePicker();
        // now define the properties of the
        // materialDateBuilder that is title text as SELECT A DATE
        materialDateBuilder2.setTitleText("SELECT A DATE");
        // now create the instance of the material date
        // picker
        final MaterialDatePicker materialDatePicker2 = materialDateBuilder.build();
// handle select date button which opens the
        // material design date picker
        mPickDateButton2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // getSupportFragmentManager() to
                        // interact with the fragments
                        // associated with the material design
                        // date picker tag is to get any error
                        // in logcat
                        materialDatePicker2.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });
        // now handle the positive button click from the
        // material design date picker
        materialDatePicker2.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        mShowSelectedDateText2.setText( materialDatePicker2.getHeaderText());
                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });
        // getting our instance
        // from Firebase Firestore.
        db= FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //image
        image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                boolean pick= true;
                if(pick==true){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }else pickImage();
                }else {
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else pickImage();
                }
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMeal.this, CurrentLocationMap.class);
                startActivityForResult(intent,1);
            }
        });
        //retrurn userId From Database
        db.collection("users")
                .orderBy("userId").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    UserModel user=  snapshot.toObject(UserModel.class);
                    user.setUserId(snapshot.getId());
                    userId=user.getId();
                }
            }});

        addMeal =findViewById(R.id.sharefood_button_ok);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timerRunning){
                    timerRunning= true;
                    duration = Integer.valueOf(timerDuration.getText().toString() );
                    duration = duration*60*60 ;
                    new CountDownTimer(  duration * 1000, 1000){
                        public void onTick(long millisUntilFinished){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String time = String.format(Locale.getDefault(),"%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)-
                                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-
                                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                                    final  String [] hourMinSec = time.split(":");
                                    hour.setText(hourMinSec[0]);
                                    min.setText(hourMinSec[1]);
                                    sec.setText(hourMinSec[2]);



                                }
                            });
                        }
                        public  void onFinish(){
                            duration = 10800;
                            timerRunning= false;
                        }
                    }.start();

                }else {
                    Toast.makeText(AddMeal.this,"Timer still running",Toast.LENGTH_SHORT).show();
                }

    if (!foodType.getText().toString().contains("non food")) {
        foodPicture.setVisibility(View.INVISIBLE);
        uploadImage();
    }
    else {
        foodPicture.setVisibility(View.VISIBLE);
    }


            }});
    }
    //methods for image
    private void pickImage() {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity().start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
    }

    private boolean checkStoragePermission() {
        boolean res2= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
    }

    private boolean checkCameraPermission() {
        boolean res1= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean res2= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }

    //    // method for increament
    public void increamentMethod( View v){
        counter++;
        number.setText(""+counter);
    }
    // method for decreament
    public void decreamentMethod(View v){
        if(counter<=0){
            counter=0;
        }
        else {  counter--;}
        number.setText(""+counter);
    }

    private void getuser (){
        FirebaseFirestore.getInstance().collection("users")
                .document(getcurrentuserid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserModel user=  documentSnapshot.toObject(UserModel.class);
                        muser = user;
                        assert user != null;

                        if (!user.getImageUrl().isEmpty()) {
                            userimage = user.getImageUrl();

                        }
                        username = user.getName();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMeal.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==Activity.RESULT_OK){
            Log.i("test","welcom");
            String loc=data.getStringExtra(CurrentLocationMap.LOCATION_OBJECT.toString());
            location.setText(loc);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uriImage  = String.valueOf(result.getUri());
                Picasso.get().load(uriImage).into(image);
                File file = new File(Uri.parse(uriImage).getPath());
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"),file));
              //d6bd9543bc1f34993039d472f5a57cd17405116a
                //e3fc49b8e2e4dc55240bfde9177dbc8e1006b4e8
                getData(filePart,"Bearer 2970c6e4dee75ac5916e2b50f81c7a7df9b1b2ba");
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void showImages() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "select picture"), CHOOSE_IMAGE);
    }


    private void addDataToFirestore(){
        photo=image.getDrawable().toString();
        title= foodTitle.getText().toString();
        details=foodDetails.getText().toString();
        loc=location.getText().toString();
        select=spinner.getSelectedItem().toString();
        numberValue=number.getText().toString();
        String ref = db.collection("MealModel").document().getId();
        Log.i("user_image2",userimage);
        MealModel mealModel =new MealModel(userId ,ref,muri,loc,0,title,details,0,username,"added","distance",Integer.parseInt(numberValue),userimage,select,Integer.valueOf(timerDuration.getText().toString()));
        mealModel.setAddedAt(System.currentTimeMillis());

        // creating a collection reference
        // for our Firebase Firetore database.
        db.collection("MealModel")
                .document(ref)
                .set(mealModel, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddMeal.this, "Your mealModel has been added to Firebase Firestore", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddMeal.this, MainPageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddMeal.this, "Fail to add meal \n" + e, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void uploadImage() {
        if(uriImage != null)
        {
            Log.d("uploadImage", "uploadImage: ");
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setMax(100);
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(Uri.parse(uriImage))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddMeal.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    muri = uri.toString();
                                    addDataToFirestore();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddMeal.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
//detect meal is food or not
        public void getData(MultipartBody.Part image, String auth){
        Retrofit retrofit = Client.getRetrofit();
        EndPoint endPoint = retrofit.create(EndPoint.class);
        Call<DetectFood> call3 = endPoint.determineFoodOrNot(image,auth);
        call3.enqueue(new Callback<DetectFood>() {
            @Override
            public void onResponse(Call<DetectFood> call, Response<DetectFood> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                foodTypes = response.body();
                for (int i=0; i<foodTypes.getFood_types().size(); i++){
                    double max=foodTypes.getFood_types().get(0).getProbs();
                    if(max>foodTypes.getFood_types().get(i).getProbs()){
                        foodType.setText(foodTypes.getFood_types().get(0).getName());
                    }
                   max=foodTypes.getFood_types().get(i).getProbs();
                    foodType.setText(foodTypes.getFood_types().get(i).getName());

                    Log.d("qq", "onResponse: "+foodTypes.getFood_types().get(i).getProbs()+" name  is "+foodTypes.getFood_types().get(i).getName());}
            }
            @Override
            public void onFailure(Call<DetectFood> call, Throwable t) {
                Log.d("tag", "onFailure: ");
            }
        });
    }

}