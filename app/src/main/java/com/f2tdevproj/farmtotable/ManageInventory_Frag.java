package com.f2tdevproj.farmtotable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ManageInventory_Frag extends Fragment {

    private static final String TAG = "ManageInventory";

    @BindView(R.id.manageinventory_frag)
    RecyclerView mRecyclerView;

    @BindView(R.id.btnAddProduce)
    Button mBtnAddProduce;

    private ManageInventory_RecyclerItemAdapter itemAdapter;
    private List<Produce> produceList;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference farmerProduceRef;
    private FirebaseAuth mAuth;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.farmer_manageinventory_frag, container, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        farmerProduceRef = mFirebaseDatabase.getReference();


        unbinder = ButterKnife.bind(this, rootView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();

        mBtnAddProduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddUpdateProduceActivity.class);
                getContext().startActivity(intent); // destroy current activity..
                startActivity(intent);
            }
        });

        farmerProduceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produceList = new ArrayList<>();
                if (dataSnapshot.child("Users").child(userID).exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Users").child(userID).child("producelist").getChildren()) {

                        String itemID = dataSnapshot1.getKey();
                        Log.d(TAG, "item = " + itemID);
                        Produce produce = dataSnapshot.child("ProduceDetails").child(itemID).getValue(Produce.class);
                        Log.d(TAG, "Produce = " + produce);
                        produce.setItemId(itemID);
                        produceList.add(produce);

                    }
                    Log.d(TAG, "Produce list = " + produceList);

                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(manager);
                    mRecyclerView.setHasFixedSize(true);
                    itemAdapter = new ManageInventory_RecyclerItemAdapter(ManageInventory_Frag.this.getActivity(), produceList);
                    mRecyclerView.setAdapter(itemAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }
}
