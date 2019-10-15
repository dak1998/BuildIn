package aadl.com.buildin.activities.guards;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import aadl.com.buildin.R;
import aadl.com.buildin.adapters.GuardItemAdapter;
import aadl.com.buildin.adapters.GuardItemAdapter;
import aadl.com.buildin.models.GuardItem;
import aadl.com.buildin.models.GuardItem;
import aadl.com.buildin.utils.NamedActionBar;

public class GuardsActivity extends AppCompatActivity {

    private NamedActionBar mActionBar;


    private RecyclerView mGuardItemsView;
    private List<GuardItem> mGuardItems;
    private GuardItemAdapter mGuardItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guards);

        initializeView();

        mGuardItemsView = findViewById(R.id.recycler_view);

        mGuardItems = new ArrayList<>();
        mGuardItemAdapter = new GuardItemAdapter(this, mGuardItems);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mGuardItemsView.setLayoutManager(mLayoutManager);

        mGuardItemsView.setItemAnimator(new DefaultItemAnimator());
        mGuardItemsView.setAdapter(mGuardItemAdapter);

        loadData();
    }

    private void loadData() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child("guards").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot s: dataSnapshot.getChildren()) {
                    GuardsActivity.this.mGuardItems.add(new GuardItem(
                            s.getKey(),
                            s.child("name").getValue(String.class),
                            s.child("contact").getValue(String.class),
                            s.child("shift_in").getValue(String.class)
                                    +" to "+
                            s.child("shift_out").getValue(String.class)
                    ));
                }

                System.out.println(mGuardItems.size());

                GuardsActivity.this.mGuardItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeView () {
        this.mActionBar = NamedActionBar.in(this).setTitle("Guards Corner").setOnBackClickListener(new NamedActionBar.AnimatedBackAction());
    }
}
