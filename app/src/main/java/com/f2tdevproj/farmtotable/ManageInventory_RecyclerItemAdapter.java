package com.f2tdevproj.farmtotable;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ManageInventory_RecyclerItemAdapter extends RecyclerView.Adapter<ManageInventory_RecyclerItemAdapter.ProduceHolder> {
    private Context context;
    private List<Produce> produceList;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public ManageInventory_RecyclerItemAdapter(Context context, List<Produce> produceList) {
        this.context = context;
        this.produceList = produceList;
    }

    @NonNull
    @Override
    public ManageInventory_RecyclerItemAdapter.ProduceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farmer_manageinventory_layout, parent, false);
        return new ManageInventory_RecyclerItemAdapter.ProduceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ManageInventory_RecyclerItemAdapter.ProduceHolder holder, final int position) {
        holder.bind(produceList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddUpdateProduceActivity.class);
                intent.putExtra("ID", produceList.get(holder.getAdapterPosition()).getItemId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return produceList.size();
    }

    class ProduceHolder extends RecyclerView.ViewHolder {

        private final TextView mProduceName;
        private final TextView mProduceCategory;
        private final TextView mProduceMinOrder;
        private final TextView mProducePricePerCollection;
        private final TextView mProduceCollectionType;
        private final TextView mProduceStock;

        public ProduceHolder(View itemView) {
            super(itemView);
            mProduceName = itemView.findViewById(R.id.manageInventory_productName);
            mProduceCategory = itemView.findViewById(R.id.manageInventory_productCategory);
            mProduceCollectionType = itemView.findViewById(R.id.manageInventory_productCollectionType);
            mProducePricePerCollection = itemView.findViewById(R.id.manageInventory_productPricePerCollection);
            mProduceStock = itemView.findViewById(R.id.manageInventory_productStock);
            mProduceMinOrder = itemView.findViewById(R.id.manageInventory_productMinOrder);
        }

        public void bind(Produce produce) {
            setProduceName(produce.getName());
            setProduceCategory(produce.getFarmname());
            setProducePricePerCollection(produce.getPricepercollection());
            setProduceCollectionType(produce.getCollectiontype());
            setProduceStock(produce.getItemQuantityChosen());
            setProduceMinOrder(produce.getMinorder());
        }

        private void setProduceName(String name){ mProduceName.setText(name);};
        private void setProduceCategory(String category){ mProduceCategory.setText(category);};
        private void setProducePricePerCollection(String price){ mProducePricePerCollection.setText(price);};
        private void setProduceCollectionType(String collectionType){mProduceCollectionType.setText(collectionType);};
        private void setProduceStock(int stock){mProduceStock.setText(String.valueOf(stock));};
        private void setProduceMinOrder(int minOrder){mProduceMinOrder.setText(String.valueOf(minOrder));};


    }
}
