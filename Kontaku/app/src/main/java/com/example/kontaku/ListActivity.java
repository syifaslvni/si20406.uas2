package com.example.kontaku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements CustomAdapter.CustomAdapterListener {

    RecyclerView mainRecyclerView;
    Button addNum;
    ArrayList<ContactModel> items = new ArrayList<>();
    CustomAdapter customAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        addNum = findViewById(R.id.addNum);
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        customAdapter = new CustomAdapter(this, items);
        mainRecyclerView.setAdapter(customAdapter);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button addNum = findViewById(R.id.addNum);
        addNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, MainActivity.class));
            }
        });

        readData();
    }

    void readData(){
        db.collection("contact").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {

                if (task.getResult() != null) {
                    items.clear();
                    for(QueryDocumentSnapshot document : (task.getResult())) {
                        ContactModel contactModel = new ContactModel(document.getId(),
                                document.getString("nama"), document.getString("nomor"));
                        items.add(contactModel);
                    }
                }
                customAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this, "Error Reading Data...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void removeDataFromCustomAdapter(int position) {
        ContactModel contactModel = items.get(position);

        db.collection("contact").document(contactModel.getId()).delete()
                .addOnSuccessListener(unused -> readData())

                .addOnFailureListener(e -> Toast.makeText(ListActivity.this, "Failed Delete Data...", Toast.LENGTH_SHORT).show());

    }
}