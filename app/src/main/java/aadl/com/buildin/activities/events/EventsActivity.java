package aadl.com.buildin.activities.events;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aadl.com.buildin.R;
import aadl.com.buildin.activities.issues.IssuesActivity;
import aadl.com.buildin.adapters.EventItemAdapter;
import aadl.com.buildin.adapters.IssueItemAdapter;
import aadl.com.buildin.models.EventItem;
import aadl.com.buildin.models.IssueItem;
import aadl.com.buildin.utils.NamedActionBar;

public class EventsActivity extends AppCompatActivity {

    private NamedActionBar mActionBar;
    private RecyclerView mEventItemsView;
    private List<EventItem> mEventItems;
    private EventItemAdapter mEventItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        initializeView();

        mEventItemsView = findViewById(R.id.recycler_view);

        mEventItems = new ArrayList<>();
        mEventItemAdapter = new EventItemAdapter(this, mEventItems);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mEventItemsView.setLayoutManager(mLayoutManager);

        mEventItemsView.setItemAnimator(new DefaultItemAnimator());
        mEventItemsView.setAdapter(mEventItemAdapter);

        loadData();
    }

    private void initializeView () {
        this.mActionBar = NamedActionBar.in(this).setTitle("Events Corner").setOnBackClickListener(new NamedActionBar.AnimatedBackAction());
    }

    private void loadData() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child("events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot s: dataSnapshot.getChildren()) {
                    EventsActivity.this.mEventItems.add(new EventItem(
                            s.getKey(),
                            s.child("title").getValue(String.class)+" (₹"+s.child("amt").getValue(String.class)+")",
                            s.child("description").getValue(String.class),
                            s.child("date").getValue(String.class)
                    ));
                }

                System.out.println(mEventItems.get(0).getDescription());

                EventsActivity.this.mEventItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
