package com.teamnightcoders.doorstep.user;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnightcoders.doorstep.user.Models.PlaceModel;
import com.teamnightcoders.doorstep.user.Models.RegionModel;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private EditText fullName, setDist, phNo, address1, address2, pinCode, landmark;
    private Button submit;
    private PlaceModel model;
    ProgressDialog progressDialog;
    int i;
    private int j = 0;
    ArrayList<String> DistArray;
    ArrayList<RegionModel> RegionArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DistArray = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("dddf");

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        init();
        setDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadData();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });


    }

    private void ReadData() {
        progressDialog.show();
        DatabaseReference mKeyRef = database.getReference("Regions");
        mKeyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DistArray.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    model = childDataSnapshot.getValue(PlaceModel.class);
                    assert model != null;
                    Log.i("Member name: ", model.getshop_region());
                    DistArray.add(model.getshop_region());
                }
                SetregionName();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }


    private void SetregionName() {


        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_singlechoice);
        for (i = 0; i < DistArray.size(); i++) {
            String temp = String.valueOf(DistArray.get(i));
            arrayAdapter.add(temp);
        }
        builderSingle.setAdapter(arrayAdapter,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                setDist.setText(strName);
                readVendors();
            }
        });
        builderSingle.show();
    }

    private void readVendors() {
        DatabaseReference VendorRef = database.getReference("Vendors");
        VendorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot VendorSnapshot) {
                RegionArraylist = new ArrayList<>();
                for (DataSnapshot childDataSnapshot1 : VendorSnapshot.getChildren()) {
                    RegionModel regModel = childDataSnapshot1.getValue(RegionModel.class);
                    RegionArraylist.add(regModel);
                    assert regModel != null;
                    Log.i("Member name: ", regModel.getClosed_dueto());
                    Log.i("Member name: ", regModel.getSeller_mail());
                    Log.i("Member name: ", regModel.getUid());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }


    private void submitData() {

        final String fullname = fullName.getText().toString();
        final String Dist = setDist.getText().toString();
        final String phno = phNo.getText().toString();
        final String Address1 = address1.getText().toString();
        final String Address2 = address2.getText().toString();
        final String pin = pinCode.getText().toString();
        final String Landmark = landmark.getText().toString();
        if (fullname.isEmpty()) {
            fullName.setError("Please enter your full name");
            fullName.requestFocus();
        } else if (Dist.isEmpty()) {
            setDist.setError("Please enter a shop name");
            setDist.requestFocus();
        } else if (phno.isEmpty()) {
            phNo.setError("Please enter the district");
            phNo.requestFocus();

        } else if (Address1.isEmpty()) {
            address1.setError("Please enter the address");
            address1.requestFocus();
        } else if (Address2.isEmpty()) {
            address2.setError("Please enter the address ");
            address2.requestFocus();
        } else if (pin.isEmpty()) {
            pinCode.setError("Please enter the pincode");
            pinCode.requestFocus();
        } else if (pin.length()!=6) {
            pinCode.setError("Please enter valid pincode");
            pinCode.requestFocus();

        } else if (pin.charAt(0)!='6') {
            pinCode.setError("Please enter valid Pincode");
            pinCode.requestFocus();

        }
                else if (pin.charAt(1) != '7'&&pin.charAt(1) != '8'&&pin.charAt(1) != '9') {
            pinCode.setError("Please enter valid pincode");
            pinCode.requestFocus();


                }

        else if (Landmark.isEmpty()) {
            landmark.setError("Enter Password again");
            landmark.requestFocus();
        } else {

            FirebaseUser user = firebaseAuth.getCurrentUser();
            assert user != null;
            String uid = user.getUid();
            DatabaseReference usersRef = database.getReference("Users")
                    .child(uid);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("uid", uid);
            hashMap.put("fullname", fullname);
            hashMap.put("usertype", "3");
            hashMap.put("contact_number", phno);
            hashMap.put("landmark", Landmark);
            hashMap.put("address1", Address1);
            hashMap.put("address2", Address2);
            hashMap.put("pin", pin);
            usersRef.setValue(hashMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                Toast.makeText(getApplicationContext(), " " +
                                                "Successfully updated",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,
                                        UserActivity.class);
                                startActivity(intent);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("HASHMAP PUSH", "Failed");
                    Toast.makeText(getApplicationContext(), "Registration failed " +
                                    "please try again",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void init() {
        fullName = findViewById(R.id.full_name);
        setDist = findViewById(R.id.region);
        phNo = findViewById(R.id.ph_no);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        landmark = findViewById(R.id.landmark);
        pinCode = findViewById(R.id.pincode);
        setDist.setClickable(true);
        setDist.setFocusable(false);
        submit = findViewById(R.id.btn_submit);


    }

    @Override
    protected void onDestroy() {
        progressDialog.dismiss();
        super.onDestroy();
    }


}