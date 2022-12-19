package com.example.reducefoodewaste.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reducefoodewaste.Models.MealModel;
import com.example.reducefoodewaste.Adapters.RecyclerViewAdapterMainPageActivity;
import com.example.reducefoodewaste.Ui.AddMeal;
import com.example.reducefoodewastebasic.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    String type;
    RecyclerView recyclerView;
    RecyclerViewAdapterMainPageActivity adapter;
    ProgressBar progressBar;
    EditText editTextSearch;
    FloatingActionButton fab;
    ImageButton imageButton;
    MealModel mealModel1;
    final ArrayList<MealModel> list = new ArrayList<>();
    List<MealModel> listOfPerson,listOfGroup,listOfVegtables,listOfFruits,listOfDrinks,listOfOthers;
    ArrayList<MealModel> listFilter=new ArrayList<>();
    Boolean user_type;
    SharedPreferences login_sharpreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.fragment_home_recyclerview);
        progressBar=view.findViewById(R.id.fragment_home_contentMainPage_progressBar_cyclic);
        editTextSearch=view.findViewById(R.id.fragment_home_editText_Searchview);
        login_sharpreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        FloatingActionButton fab =view.findViewById(R.id.fragment_home_button_fab);

        user_type = login_sharpreferences.getBoolean("user_type",false);


        if (user_type){
            fab.setVisibility(View.VISIBLE);
        }else {
            fab.setVisibility(View.GONE);
        }

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listFilter.clear();
                if(editable.toString().isEmpty()){
                    recyclerView.setAdapter(new RecyclerViewAdapterMainPageActivity(getContext(),list));
                    adapter.notifyDataSetChanged();
                }
                else{
                    Filter(editable.toString());
                }
            }
        });
        fab=view.findViewById(R.id.fragment_home_button_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMeal.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void Filter(String text) {
        for (MealModel mealModel : list) {
            if (mealModel.getMealName().toLowerCase().contains(text)) {
                listFilter.add(mealModel);
            }
        }
        recyclerView.setAdapter(new RecyclerViewAdapterMainPageActivity(getContext(),listFilter));
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();

        setrec();

    }

    private void setrec(){
        Log.i("onresumm","enter");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("MealModel")
                .orderBy("mealName").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                list.clear();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    mealModel1 = snapshot.toObject(MealModel.class);
                    list.add(mealModel1);
                    type=mealModel1.getFoodtype();

                }
                adapter = new RecyclerViewAdapterMainPageActivity(getContext(), list);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });

    }
    public  void filterType() {
        Log.d("alhassna", "filterType: "+type);
        if (type.contains("Person")) {
            list.clear();
            listOfPerson.add(mealModel1);
            adapter = new RecyclerViewAdapterMainPageActivity(getContext(), listOfPerson);

        } else if (type.contains("Group")) {
            list.clear();
            listOfGroup.add(mealModel1);
            adapter = new RecyclerViewAdapterMainPageActivity(getContext(), listOfGroup);
        } else if (type.contains("Vegetables")) {
            list.clear();
            listOfVegtables.add(mealModel1);
            adapter = new RecyclerViewAdapterMainPageActivity(getContext(), listOfVegtables);
        } else if (type.contains("Fruits")) {
            list.clear();
            listOfFruits.add(mealModel1);
            adapter = new RecyclerViewAdapterMainPageActivity(getContext(), listOfFruits);
        } else if (type.contains("Drinks")) {
            list.clear();
            listOfDrinks.add(mealModel1);
            adapter = new RecyclerViewAdapterMainPageActivity(getContext(), listOfDrinks);
        } else if (type.contains("Others")) {
            list.clear();
            listOfOthers.add(mealModel1);
            adapter = new RecyclerViewAdapterMainPageActivity(getContext(), listOfOthers);
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        progressBar.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }
}

