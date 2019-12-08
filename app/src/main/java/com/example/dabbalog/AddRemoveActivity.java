package com.example.dabbalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRemoveActivity extends AppCompatActivity {

    EditText etName,etRDate;
    Button btnSubmit;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove);

        final DatabaseReference dRef1 = databaseReference.child("UserData");

        etName=findViewById(R.id.etName);
        etRDate=findViewById(R.id.etRDate);
        btnSubmit=findViewById(R.id.btnSubmit);

        final String  flag = getIntent().getStringExtra("Flag");

        if(flag.equals("0")){
            etRDate.setVisibility(View.GONE);
            btnSubmit.setText("Remove Client");
        }
        else
            btnSubmit.setText("Add Client");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(etRDate.getText().toString().isEmpty() || etName.getText().toString().isEmpty()) && flag.equals("1"))
                {
                    String Name = etName.getText().toString();
                    String date=etRDate.getText().toString();
                    String count = "0";

                    User userData = new User(count,date,Name);
                    dRef1.child(Name).setValue(userData);
                    Toast.makeText(AddRemoveActivity.this,"Client Added Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddRemoveActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else if(!(etName.getText().toString().isEmpty() || flag.equals("1")))
                {
                    String Name = etName.getText().toString();
                    dRef1.child(Name).removeValue();
                    Toast.makeText(AddRemoveActivity.this,"Client Removed",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddRemoveActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(AddRemoveActivity.this,"Fill all Fields",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
