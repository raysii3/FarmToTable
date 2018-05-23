package com.f2tdevproj.farmtotable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProduceDetailActivity extends AppCompatActivity {
    private static final String TAG = "ProduceDetails";

    protected static final Query sProduceQuery =
            FirebaseDatabase.getInstance().getReference().child("ProduceDetails");

    @BindView(R.id.produceDetail_description)
    TextView description;

    @BindView(R.id.produceDetail_minOrder)
    TextView minorder;

    @BindView(R.id.produceDetail_stock)
    TextView stock;

    @BindView(R.id.produceDetail_itemQuantityChosen)
    EditText quantityChosen;

    @BindView(R.id.produceDetail_pricePerCollection)
    TextView pricePerCollection;

    @BindView(R.id.btnAddToCart)
    Button btnAddToCart;

    @BindView(R.id.produceDetail_name)
    TextView name;

    @BindView(R.id.produceDetail_collectionType)
    TextView collectionType;

    @BindView(R.id.produceDetail_collectionType2)
    TextView collectionType2;

    @BindView(R.id.produceDetail_collectionType3)
    TextView collectionType3;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produce_detail_frag);

        String id = getIntent().getStringExtra("ID");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("UserShoppingCart");


        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Submit pressed");
                int quantity = Integer.parseInt(quantityChosen.getText().toString());
                int tempMinOrder = Integer.parseInt(minorder.getText().toString());
                if(quantity > tempMinOrder){
                    Intent intent = new Intent(ProduceDetailActivity.this, ShoppingCart_Frag.class);

                    ProduceDetailActivity.this.startActivity(intent);
                }
            }
        });

        sProduceQuery.startAt(id).endAt(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Produce produce = dataSnapshot.getValue(Produce.class);
                produce.setItemId(dataSnapshot.getKey());
                Log.d(TAG, "Produce Details: \n" + produce);
                name.setText(produce.getName());
                description.setText(produce.getDescription());
                minorder.setText(produce.getMinorder());
                stock.setText(produce.getStock());
                pricePerCollection.setText(produce.getPricepercollection());
                collectionType.setText(produce.getCollectiontype());
                collectionType2.setText(produce.getCollectiontype());
                collectionType3.setText(produce.getCollectiontype());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ButterKnife.bind(this);
    }

}
