package com.example.dabbalog;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    //6.2
    private ArrayList<User> mExampleList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    //1
    Context context;

    public ExampleAdapter(Context context, ArrayList<User>e) {
        this.context = context;
        mExampleList= e;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public Button btnDecCount,btnIncCount;
        public RelativeLayout relativeLayout;
        //2
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            //4
            tvName = itemView.findViewById(R.id.tvName);
            btnIncCount = itemView.findViewById(R.id.btnIncCount);
            btnDecCount = itemView.findViewById(R.id.btnDecCount);
            relativeLayout=itemView.findViewById(R.id.cdUser);
        }
    }

    //6.1

    //3
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //5
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(view);
        return exampleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder exampleViewHolder, int i) {
        //7

        final User currentItem = mExampleList.get(i);
        exampleViewHolder.tvName.setText(currentItem.getName());


        exampleViewHolder.btnIncCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=currentItem.getName();
                update(name,true);
                mExampleList.clear();
                exampleViewHolder.btnIncCount.setClickable(false);
                exampleViewHolder.btnIncCount.setBackgroundResource(R.drawable.btnclicked);
            }
        });

        exampleViewHolder.btnDecCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=currentItem.getName();
                update(name,false);
                mExampleList.clear();
                exampleViewHolder.btnDecCount.setClickable(false);
                exampleViewHolder.btnDecCount.setBackgroundResource(R.drawable.btnclicked);
            }
        });

        exampleViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,UserDetailsActivity.class);
                intent.putExtra("Name",currentItem.getName());
                context.startActivity(intent);
            }
        });

    }

    void update(String name, final boolean flag){
        final DatabaseReference dRef1 = databaseReference.child("UserData").child(name);
        dRef1.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String count= dataSnapshot.getValue(String.class);

                if(flag)
                {
                    int count1 = Integer.parseInt(count);
                    count1++;
                    dRef1.child("count").setValue(String.valueOf(count1));
                }
                else
                {
                    int count1 = Integer.parseInt(count);
                    if(count1>0){
                        count1--;
                        dRef1.child("count").setValue(String.valueOf(count1));
                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}