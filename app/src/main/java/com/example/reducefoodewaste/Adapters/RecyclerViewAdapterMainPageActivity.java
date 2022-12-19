package com.example.reducefoodewaste.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.reducefoodewaste.Models.MealModel;
import com.example.reducefoodewaste.Models.UserModel;
import com.example.reducefoodewaste.Ui.MealFeaturesActivity;
import com.example.reducefoodewastebasic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RecyclerViewAdapterMainPageActivity extends
        RecyclerView.Adapter<RecyclerViewAdapterMainPageActivity.ViewHolder>  {
    public String TAG="FAVOURITE";
int count;
    public  static  final  String  keyName="Mealname";
    List<MealModel> list;
    Context context;
    MealModel mealModel;
    SparseBooleanArray checkedItemPositions;
    public RecyclerViewAdapterMainPageActivity(Context c, List<MealModel> list) {
        context=c;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_view_main_page, parent, false);
        ViewHolder viewHolder = new ViewHolder(view2);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mealModel = list.get(holder.getAdapterPosition());
       holder.username.setText(mealModel.getUserName());
        holder.mealName.setText(mealModel.getMealName());
        holder.distance.setText(mealModel.getLocation());
        holder.duration.setText(String.valueOf(mealModel.getDuration()));
        if(mealModel.getIsRequested()!=null&&mealModel.getIsRequested().equals("true")){
            holder.card.setCardBackgroundColor(Color.parseColor("#E6E6E6"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Sorry, this item is already token!",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.card.setCardElevation(5);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, MealFeaturesActivity.class);
                    i.putExtra(keyName,list.get(position));
                    holder.itemView. getContext().startActivity(i);
                }
            });
        }
        holder.view.setText(String.valueOf(mealModel.getView()));
        String url_image = mealModel.getPhoto();
        if(mealModel.getAddedAt()!=0) {
            // Method for difference
            Date starttime = new Date(mealModel.getAddedAt());
            Long tsLong = System.currentTimeMillis();
            Date endtime = new Date(tsLong);
            holder.addedAt.setText(String.valueOf(computeDiff(starttime, endtime).get(TimeUnit.HOURS))+" "+"hours ago");
        }
        Picasso.get().load(url_image).into(holder.photo);
        Glide.with(context)
                .load(url_image)
                .placeholder(R.drawable.progress_animation)
                .into(holder.photo);

        Log.i("user_image",mealModel.getUserImage()+"empty");
        Log.i("user_id",mealModel.getMealid());
        Glide.with(context)
                .load(mealModel.getUserImage())
               // .placeholder(R.drawable.progress_animation)
                .into(holder.user_image);

        Picasso.get()
                .load(url_image)
                .placeholder(R.drawable.progress_animation)
                .into(holder.photo);

        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("users")
                .orderBy("userId").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    UserModel user=  snapshot.toObject(UserModel.class);
                    count=user.getFav();
                }
            }});



        holder.fav_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.fav_image.isChecked()) {
                    holder.fav.setText(String.valueOf(count+1));
                }
                else {
                    holder.fav.setText(String.valueOf(count));
                }
            }
        });



    }


    public static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {

        long diffInMillies = date2.getTime() - date1.getTime();

        //create the list
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        //create the result map of TimeUnit and difference
        Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
        long milliesRest = diffInMillies;

        for ( TimeUnit unit : units ) {

            //calculate difference in millisecond
            long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;

            //put the result in the map
            result.put(unit,diff);
        }

        return result;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<MealModel> mList) {
        list=mList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView  photo;
        ImageView user_image;
        CheckBox fav_image;
        CardView card;
        TextView duration ;
        TextView view, mealName, distance, username,addedAt,fav;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            user_image = itemView.findViewById(R.id.img);
            fav=itemView.findViewById(R.id.favourite);
            photo = itemView.findViewById(R.id.list_view_mainPage_imageView_meal);
            view =itemView.findViewById(R.id.view_counter   );
            username = itemView.findViewById(R.id.list_view_mainPage_textView_userName);
            mealName = itemView.findViewById(R.id.list_view_mainPage_textView_name_meal);
            distance = itemView.findViewById(R.id.location);
            addedAt=itemView.findViewById(R.id.list_view_mainPage_textView_justadded);
            fav_image=itemView.findViewById(R.id.fvimg);
            duration = itemView.findViewById(R.id.duration);
        }
    }


}