package com.teamnightcoders.doorstep.user.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnightcoders.doorstep.user.MainActivity;
import com.teamnightcoders.doorstep.user.R;
import com.teamnightcoders.doorstep.user.UserActivity;

import java.util.HashMap;

public class MyCartFragment extends Fragment {
    Button addTocart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String cartItem;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container,false);
        addTocart = view.findViewById(R.id.AddtoCart);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        String uid = firebaseUser.getUid();
        databaseReference = firebaseDatabase.getReference("Users").child(uid).child("Cart");
         cartItem = "andi";
        addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddtoCart();
            }
        });
        return view;
    }

    private void AddtoCart() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Items", cartItem);
        databaseReference.setValue(hashMap)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(getActivity(), " " +
                                    "Successfully updated",Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("HASHMAP PUSH", "Failed");
                Toast.makeText(getActivity(), "Registration failed " +
                                "please try again",
                        Toast.LENGTH_SHORT).show();
            }
        });




    }
}

