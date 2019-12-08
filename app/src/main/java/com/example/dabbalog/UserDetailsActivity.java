package com.example.dabbalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetailsActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    TextView tvName,tvCount,tvDate,tvAmt;
    Button btnRemove;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        tvCount=findViewById(R.id.tvCount);
        tvName=findViewById(R.id.tvName);
        tvDate=findViewById(R.id.tvDate);
        tvAmt=findViewById(R.id.bill);
        btnRemove=findViewById(R.id.Remove);
        progressDialog=new ProgressDialog(this);
        final String name=getIntent().getStringExtra("Name");

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Removing");
                DatabaseReference remove = databaseReference.child("UserData").child(name);

               // progressDialog.show();
                Intent intent = new Intent(UserDetailsActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(UserDetailsActivity.this,"Client Removed",Toast.LENGTH_SHORT).show();

                remove.removeValue();
              //  progressDialog.cancel();


            }
        });
        DatabaseReference dRef1 = databaseReference.child("UserData").child(name);

        tvName.setText("* Name : "+name);

        dRef1.child("count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String count = dataSnapshot.getValue(String.class);
                int count1 = Integer.parseInt(count);
                tvCount.setText("* Days : "+ count);
                tvAmt.setText("Your Amount is : "+count1*50);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        dRef1.child("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String date = dataSnapshot.getValue(String.class);
                tvDate.setText("* Date : "+ date);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
