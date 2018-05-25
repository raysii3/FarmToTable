package com.f2tdevproj.farmtotable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

public class ShoppingCart_Frag extends Fragment {

    private static final String TAG = "ShoppingCart";

//    @BindView(R.id.btnCheckOut)
    Button btnCheckOut;

//    @BindView(R.id.chkbox_selectAll)
    CheckBox chkBoxSelectAll;

//    @BindView(R.id.shoppingCart_frag)
    RecyclerView mRecyclerView;

    private ShoppingCart_RecyclerItemAdapter itemAdapter;
    private List<Produce> produceList;

    private Unbinder unbinder;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference shoppingCartRef;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shopping_cart_frag, container, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        shoppingCartRef = mFirebaseDatabase.getReference();
        Query produceRef = shoppingCartRef.child("ProduceDetails");

        mRecyclerView = rootView.findViewById(R.id.shoppingCart_frag);
        btnCheckOut = rootView.findViewById(R.id.btnCheckOut);
        chkBoxSelectAll = rootView.findViewById(R.id.chkbox_selectAll);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();

        shoppingCartRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produceList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.child("UserShoppingCart").child(userID).getChildren()){

                    String itemID = dataSnapshot1.getKey();
                    int itemQuantity = (int) (long) dataSnapshot1.child("quantityChosen").getValue();
                    Log.d(TAG, "item = " + itemID + ", itemQuantity = " + itemQuantity);


                    Produce produce = dataSnapshot.child("ProduceDetails").child(itemID).getValue(Produce.class);
                    Log.d(TAG, "Produce = " + produce);
                    produce.setItemId(itemID);
                    produce.setItemQuantityChosen(itemQuantity);
                    produceList.add(produce);

                }
                Log.d(TAG, "Produce list = " + produceList);

                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setHasFixedSize(true);
                itemAdapter = new ShoppingCart_RecyclerItemAdapter(ShoppingCart_Frag.this.getActivity(), produceList);
                mRecyclerView.setAdapter(itemAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        attachRecyclerViewAdapter();
//    }
//
//    @Override public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    private void attachRecyclerViewAdapter() {
//        final RecyclerView.Adapter adapter = newAdapter();
//
//        /*// Scroll to bottom on new messages
//        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                mRecyclerView.smoothScrollToPosition(adapter.getItemCount());
//            }
//        });*/
//
//        mRecyclerView.setAdapter(adapter);
//    }
//
//
//    protected RecyclerView.Adapter newAdapter() {
//        FirebaseRecyclerOptions<Produce> options =
//                new FirebaseRecyclerOptions.Builder<Produce>()
//                        .setQuery(shoppingCartQuery, new SnapshotParser<Produce>() {
//                            @NonNull
//                            @Override
//                            public Produce parseSnapshot(@NonNull DataSnapshot snapshot) {
//                                Produce produce = snapshot.getValue(Produce.class);
//                                String Id = snapshot.getKey();
//                                produce.setItemId(Id);
//                                Log.d(TAG, "Produce = " + produce);
//                                return produce;
//                            }
//                        })
//                        .setLifecycleOwner(this)
//                        .build();
//
//        return new FirebaseRecyclerAdapter<Produce, ShoppingCart_Frag.ProduceHolder>(options) {
//
//            private SparseBooleanArray itemStateArray = new SparseBooleanArray();
//            @NonNull
//            @Override
//            public ShoppingCart_Frag.ProduceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.produce_catalog_layout, parent, false);
//                return new ShoppingCart_Frag.ProduceHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull final ShoppingCart_Frag.ProduceHolder holder, final int position, @NonNull final Produce model) {
//                final int adaptorPosition = holder.getAdapterPosition();
//                holder.bind(model, adaptorPosition, itemStateArray);
//                holder.mProduceCheckBox.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (!itemStateArray.get(adaptorPosition, false)) {
//                            holder.mProduceCheckBox.setChecked(true);
//                            itemStateArray.put(adaptorPosition, true);
//                        }
//                        else  {
//                            holder.mProduceCheckBox.setChecked(false);
//                            itemStateArray.put(adaptorPosition, false);
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onDataChanged() {
//
//            }
//        };
//    }
//
//
//    public static class ProduceHolder extends RecyclerView.ViewHolder{
//
//        private final TextView mProduceName;
//        private final TextView mProduceFarm;
//        private final TextView mProducePricePerCollection;
//        private final TextView mProduceCollectionType;
//        private final EditText mProduceQuantityChosen;
//        private CheckBox mProduceCheckBox;
//        private Button btnAdd;
//        private Button btnMinus;
//
//
//        public ProduceHolder(View itemView) {
//            super(itemView);
//            mProduceName = itemView.findViewById(R.id.shoppingCart_name);
//            mProduceFarm = itemView.findViewById(R.id.shoppingCart_farmName);
//            mProduceCollectionType = itemView.findViewById(R.id.shoppingCart_collectionType);
//            mProducePricePerCollection = itemView.findViewById(R.id.shoppingCart_price);
//            mProduceQuantityChosen = itemView.findViewById(R.id.shoppingCart_quantityChosen);
//            mProduceCheckBox = itemView.findViewById(R.id.chkbox_Produce);
//        }
//
//        public void bind(Produce produce,int position, SparseBooleanArray itemStateArray) {
//            setProduceName(produce.getName());
//            setProduceFarmName(produce.getFarmname());
//            setProducePricePerCollection(produce.getPricepercollection());
//            setProduceCollectionType(produce.getCollectiontype());
//            setProduceQuantityChosen(produce.getItemQuantityChosen());
//
//            if (!itemStateArray.get(position, false)) {
//                mProduceCheckBox.setChecked(false);}
//            else {
//                mProduceCheckBox.setChecked(true);
//            }
//
//        }
//
//        private void setProduceName(String name){ mProduceName.setText(name);};
//        private void setProduceFarmName(String description){ mProduceFarm.setText(description);};
//        private void setProducePricePerCollection(String price){ mProducePricePerCollection.setText(price);};
//        private void setProduceCollectionType(String collectionType){mProduceCollectionType.setText(collectionType);};
//        private void setProduceQuantityChosen(int quantityChosen){mProduceQuantityChosen.setText(quantityChosen);};
//
//    }

}
