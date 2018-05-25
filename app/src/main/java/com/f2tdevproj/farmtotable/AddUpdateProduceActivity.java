package com.f2tdevproj.farmtotable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddUpdateProduceActivity extends AppCompatActivity {

    private static final String TAG = "ProduceDetails";

    protected static final Query sProduceQuery =
            FirebaseDatabase.getInstance().getReference().child("ProduceDetails");
    @BindView(R.id.updateAddInventory_productDescription)
    EditText description;

    @BindView(R.id.updateAddInventory_productMinOrder)
    EditText minOrder;

    @BindView(R.id.updateAddInventory_productStock)
    EditText stock;

    @BindView(R.id.updateAddInventory_productPricePerCollection)
    EditText pricePerCollection;

    @BindView(R.id.updateAddInventory_productId)
    EditText productId;

    @BindView(R.id.updateAddInventory_productCategory)
    Spinner category;

    @BindView(R.id.updateAddInventory_productName)
    EditText name;

    @BindView(R.id.updateAddInventory_productCollectionType)
    Spinner collectionType;

    @BindView(R.id.UpdateProductButton)
    Button btnSubmit;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference produceDetailsRef, productCategoryRef;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produce_detail_frag);

        final String id = getIntent().getStringExtra("ID");
        Log.d(TAG, "Produce selected = " + id);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        produceDetailsRef = mFirebaseDatabase.getReference("ProduceDetails");
        productCategoryRef = mFirebaseDatabase.getReference("ProductCategory");
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        ButterKnife.bind(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!id.isEmpty() || id == null){
                    produceDetailsRef.child(id).child("name").setValue(name.getText());
                    produceDetailsRef.child(id).child("description").setValue(description.getText());
                    produceDetailsRef.child(id).child("minorder").setValue(minOrder.getText());
                    produceDetailsRef.child(id).child("stock").setValue(stock.getText());
                    productCategoryRef.child(category.getSelectedItem().toString()).child(id).setValue(true);
                    produceDetailsRef.child(id).child("category").setValue(category.getSelectedItem().toString());
                    produceDetailsRef.child(id).child("collectiontype").setValue(collectionType.getSelectedItem().toString());

                    Intent intent = new Intent(AddUpdateProduceActivity.this, FarmerMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears all previous activities task
                    finish(); // destroy current activity..
                    startActivity(intent);

                }else{
                    String newId = produceDetailsRef.push().getKey();
                    produceDetailsRef.child(newId).child("name").setValue(name.getText());
                    produceDetailsRef.child(newId).child("description").setValue(description.getText());
                    produceDetailsRef.child(newId).child("minorder").setValue(minOrder.getText());
                    produceDetailsRef.child(newId).child("stock").setValue(stock.getText());
                    productCategoryRef.child(category.getSelectedItem().toString()).child(newId).setValue(true);
                    produceDetailsRef.child(newId).child("category").setValue(category.getSelectedItem().toString());
                    produceDetailsRef.child(newId).child("collectiontype").setValue(collectionType.getSelectedItem().toString());

                    Intent intent = new Intent(AddUpdateProduceActivity.this, FarmerMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears all previous activities task
                    finish(); // destroy current activity..
                    startActivity(intent);

                }
            }
        });

        sProduceQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Produce produce = dataSnapshot.child(id).getValue(Produce.class);
                Log.d(TAG, "Produce Details: \n" + produce);
                produce.setItemId(id);

                name.setText(produce.getName());
                description.setText(produce.getDescription());
                minOrder.setText(String.valueOf(produce.getMinorder()));
                stock.setText(String.valueOf(produce.getStock()));
                pricePerCollection.setText(produce.getPricepercollection());
                productId.setText(produce.getItemId());
                category.setSelection(getIndex(category, produce.getCategory()));
                collectionType.setSelection(getIndex(collectionType, produce.getCollectiontype()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}