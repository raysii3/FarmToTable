package com.f2tdevproj.farmtotable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShoppingCart_Frag extends Fragment {

    @BindView(R.id.ShoppingCart_ExpandableItemList)
    ExpandableListView listView;

    @BindView(R.id.ShoppingCart_ExpandableItemList)
    Button btnCheckAll;


    private Unbinder unbinder;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference shoppingCartRef;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.produce_catalog_frag, container, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        shoppingCartRef = mFirebaseDatabase.getReference("UserShoppingCart");



        unbinder = ButterKnife.bind(this, rootView);



        return rootView;
    }
}
