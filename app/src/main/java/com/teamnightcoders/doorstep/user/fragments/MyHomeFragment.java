package com.teamnightcoders.doorstep.user.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.teamnightcoders.doorstep.user.LoginActivity;
import com.teamnightcoders.doorstep.user.R;

public class MyHomeFragment extends Fragment {
    private Button logout;
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_home,container,false);
        logout=view.findViewById(R.id.log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(mContext, LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}

