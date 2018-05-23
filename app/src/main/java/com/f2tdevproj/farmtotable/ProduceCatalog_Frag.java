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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ProduceCatalog_Frag extends Fragment{
    private static final String TAG = "ProduceCatalog_Frag";

    protected static final Query sProduceQuery =
            FirebaseDatabase.getInstance().getReference().child("ProduceDetails");


    @BindView(R.id.produceCatalog_frag)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.produce_catalog_frag, container, false);


        //Initializes Layout Manager.
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        unbinder = ButterKnife.bind(this, rootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        attachRecyclerViewAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void attachRecyclerViewAdapter() {
        final RecyclerView.Adapter adapter = newAdapter();

        /*// Scroll to bottom on new messages
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });*/

        mRecyclerView.setAdapter(adapter);
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

        return new FirebaseRecyclerAdapter<Produce, ProduceHolder>(options) {
           @NonNull
           @Override
            public ProduceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.produce_catalog_layout, parent, false);
                return new ProduceHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProduceHolder holder, int position, @NonNull final Produce model) {
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
        private final TextView mProduceStock;
        private final TextView mProducePricePerCollection;
        private final TextView mProduceCollectionType;
        private final TextView mProduceCollectionType2;



        public ProduceHolder(View itemView) {
            super(itemView);
            mProduceName = itemView.findViewById(R.id.produceList_name);
            mProduceFarm = itemView.findViewById(R.id.produceList_farmName);
            mProduceStock = itemView.findViewById(R.id.produceList_stock);
            mProduceCollectionType = itemView.findViewById(R.id.collectionType);
            mProduceCollectionType2 = itemView.findViewById(R.id.collectionType2);
            mProducePricePerCollection = itemView.findViewById(R.id.produceList_pricePerCollection);
        }

        public void bind(Produce produce) {
            setProduceName(produce.getName());
            setProduceFarmName(produce.getFarmname());
            setProduceStock(produce.getStock());
            setProducePricePerCollection(produce.getPricepercollection());
            setmProduceCollectionType(produce.getCollectiontype());
            setmProduceCollectionType2(produce.getCollectiontype());
        }

        private void setProduceName(String name){ mProduceName.setText(name);};
        private void setProduceFarmName(String description){ mProduceFarm.setText(description);};
        private void setProduceStock(int quantity){ mProduceStock.setText(String.valueOf(quantity));};
        private void setProducePricePerCollection(String price){ mProducePricePerCollection.setText(price);};
        private void setmProduceCollectionType(String collectionType){mProduceCollectionType.setText(collectionType);};
        private void setmProduceCollectionType2(String collectionType2){mProduceCollectionType2.setText(collectionType2);};


    }


}
