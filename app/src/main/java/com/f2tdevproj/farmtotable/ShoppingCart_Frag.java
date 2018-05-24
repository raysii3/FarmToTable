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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShoppingCart_Frag extends Fragment {

    protected static final Query sProduceQuery =
            FirebaseDatabase.getInstance().getReference().child("ProduceDetails");

    private static final String TAG = "ShoppingCart";

    @BindView(R.id.btnCheckOut)
    Button btnCheckAll;

    @BindView(R.id.chkbox_selectAll)
    CheckBox chkBoxSelectAll;

    @BindView(R.id.shoppingCart_frag)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference shoppingCartRef;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shopping_cart_layout, container, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        shoppingCartRef = mFirebaseDatabase.getReference("UserShoppingCart");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    protected RecyclerView.Adapter newAdapter() {
        FirebaseRecyclerOptions<Produce> options =
                new FirebaseRecyclerOptions.Builder<Produce>()
                        .setQuery(sProduceQuery, new SnapshotParser<Produce>() {
                            @NonNull
                            @Override
                            public Produce parseSnapshot(@NonNull DataSnapshot snapshot) {
                                Produce produce = snapshot.getValue(Produce.class);
                                String Id = snapshot.getKey();
                                produce.setItemId(Id);
                                Log.d(TAG, "Produce = " + produce);
                                return produce;
                            }
                        })
                        .setLifecycleOwner(this)
                        .build();

        return new FirebaseRecyclerAdapter<Produce, ProduceCatalog_Frag.ProduceHolder>(options) {
            @NonNull
            @Override
            public ProduceCatalog_Frag.ProduceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.produce_catalog_layout, parent, false);
                return new ProduceCatalog_Frag.ProduceHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProduceCatalog_Frag.ProduceHolder holder, int position, @NonNull final Produce model) {
                holder.bind(model);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ProduceDetailActivity.class);
                        intent.putExtra("ID", model.getItemId());
                        getContext().startActivity(intent);
                    }
                });
            }

            @Override
            public void onDataChanged() {

            }
        };
    }

    public static class ProduceHolder extends RecyclerView.ViewHolder{

        private final TextView mProduceName;
        private final TextView mProduceFarm;
        private final TextView mProducePricePerCollection;
        private final TextView mProduceCollectionType;
        private final EditText mProduceQuantityChosen;



        public ProduceHolder(View itemView) {
            super(itemView);
            mProduceName = itemView.findViewById(R.id.shoppingCart_name);
            mProduceFarm = itemView.findViewById(R.id.produceList_farmName);
            mProduceCollectionType = itemView.findViewById(R.id.collectionType);
            mProducePricePerCollection = itemView.findViewById(R.id.produceList_pricePerCollection);
            mProduceQuantityChosen = itemView.findViewById(R.id.shoppingCart_QuantityChosen);
        }

        public void bind(Produce produce) {
            setProduceName(produce.getName());
            setProduceFarmName(produce.getFarmname());
            setProducePricePerCollection(produce.getPricepercollection());
            setmProduceCollectionType(produce.getCollectiontype());
        }

        private void setProduceName(String name){ mProduceName.setText(name);};
        private void setProduceFarmName(String description){ mProduceFarm.setText(description);};
        private void setProducePricePerCollection(String price){ mProducePricePerCollection.setText(price);};
        private void setmProduceCollectionType(String collectionType){mProduceCollectionType.setText(collectionType);};
    }

}
