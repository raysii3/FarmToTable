package com.f2tdevproj.farmtotable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ShoppingCart_RecyclerItemAdapter extends RecyclerView.Adapter<ShoppingCart_RecyclerItemAdapter.ProduceHolder>{

    private Context context;
    private List<Produce> produceList;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public ShoppingCart_RecyclerItemAdapter(Context context, List<Produce> produceList) {
        this.context = context;
        this.produceList = produceList;
    }

    @NonNull
    @Override
    public ShoppingCart_RecyclerItemAdapter.ProduceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_cart_layout, parent, false);
        return new ProduceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCart_RecyclerItemAdapter.ProduceHolder holder, int position) {
        holder.bind(produceList.get(position));
    }

    @Override
    public int getItemCount() {
        return produceList.size();
    }

    class ProduceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mProduceName;
        private final TextView mProduceFarm;
        private final TextView mProducePricePerCollection;
        private final TextView mProduceCollectionType;
        private final EditText mProduceQuantityChosen;
        private CheckBox mProduceCheckBox;
        private Button btnAdd;
        private Button btnMinus;


        public ProduceHolder(View itemView) {
            super(itemView);
            mProduceName = itemView.findViewById(R.id.shoppingCart_name);
            mProduceFarm = itemView.findViewById(R.id.shoppingCart_farmName);
            mProduceCollectionType = itemView.findViewById(R.id.shoppingCart_collectionType);
            mProducePricePerCollection = itemView.findViewById(R.id.shoppingCart_price);
            mProduceQuantityChosen = itemView.findViewById(R.id.shoppingCart_quantityChosen);
            mProduceCheckBox = itemView.findViewById(R.id.chkbox_Produce);
            btnAdd = itemView.findViewById(R.id.shoppingCart_btnAdd);
            btnMinus = itemView.findViewById(R.id.shoppingCart_btnMinus);
        }

        public void bind(Produce produce) {
            setProduceName(produce.getName());
            setProduceFarmName(produce.getFarmname());
            setProducePricePerCollection(produce.getPricepercollection());
            setProduceCollectionType(produce.getCollectiontype());
            setProduceQuantityChosen(produce.getItemQuantityChosen());
        }

        private void setProduceName(String name){ mProduceName.setText(name);};
        private void setProduceFarmName(String description){ mProduceFarm.setText(description);};
        private void setProducePricePerCollection(String price){ mProducePricePerCollection.setText(price);};
        private void setProduceCollectionType(String collectionType){mProduceCollectionType.setText(collectionType);};
        private void setProduceQuantityChosen(int quantityChosen){mProduceQuantityChosen.setText(String.valueOf(quantityChosen));};

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            int i = view.getId();
            if (i == R.id.chkbox_Produce) {
                if (!itemStateArray.get(adapterPosition, false)) {
                    mProduceCheckBox.setChecked(true);
                    itemStateArray.put(adapterPosition, true);
                }
                else  {
                    mProduceCheckBox.setChecked(false);
                    itemStateArray.put(adapterPosition, false);
                }
            }else if(i == R.id.shoppingCart_btnAdd){
                int num = Integer.parseInt(mProduceQuantityChosen.getText().toString());
                mProduceQuantityChosen.setText(String.valueOf(num + 1));
            }else if(i == R.id.shoppingCart_btnMinus){
                int num = Integer.parseInt(mProduceQuantityChosen.getText().toString());
                if(num < 1){
                    mProduceQuantityChosen.setText("1");
                }else{
                    mProduceQuantityChosen.setText(String.valueOf(num - 1));
                }
            }
        }

    }
}
