package com.example.kontaku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    EditText mainNama, mainNomor;
    Button mainSave;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String collectionContact = "contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainNama = findViewById(R.id.mainNama);
        mainNomor = findViewById(R.id.mainNomor);
        mainSave = findViewById(R.id.mainSave);

        mainSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                startActivity(new Intent(getApplicationContext(), ListActivity.class));
            }
        });
    }

    void addData(){
        String nama = mainNama.getText().toString();
        String nomor = mainNomor.getText().toString();
        ContactModel contactModel = new ContactModel(null, nama, nomor);

        db.collection(collectionContact).add(contactModel)
                .addOnSuccessListener(documentReference -> Toast.makeText(MainActivity.this, "Succes!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show());
    }

}