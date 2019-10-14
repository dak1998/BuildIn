package aadl.com.buildin.activities.issues;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aadl.com.buildin.MainActivity;
import aadl.com.buildin.R;
import aadl.com.buildin.adapters.HomeItemAdapter;
import aadl.com.buildin.adapters.IssueItemAdapter;
import aadl.com.buildin.models.IssueItem;
import aadl.com.buildin.utils.NamedActionBar;
import aadl.com.buildin.utils.Redirector;

public class IssuesActivity extends AppCompatActivity {

    private NamedActionBar mActionBar;
    private RecyclerView mIssueItemsView;
    private IssueItemAdapter mIssueItemAdapter;
    private List<IssueItem> mIssueItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);


        this.initializeView();

        mIssueItemsView = findViewById(R.id.recycler_view);

        mIssueItems = new ArrayList<>();
        mIssueItemAdapter = new IssueItemAdapter(this, mIssueItems);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mIssueItemsView.setLayoutManager(mLayoutManager);

        mIssueItemsView.setItemAnimator(new DefaultItemAnimator());
        mIssueItemsView.setAdapter(mIssueItemAdapter);

        this.loadData();
    }

    private void loadData() {
        DatabaseReference db =FirebaseDatabase.getInstance().getReference();

        db.child("issues").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot s: dataSnapshot.getChildren()) {
                    IssuesActivity.this.mIssueItems.add(new IssueItem(
                            s.getKey(),
                       s.child("title").getValue(String.class),
                       s.child("description").getValue(String.class),
                            0,
                            0,
                            Arrays.asList(s.child("image_data/0").getValue(String.class))
                    ));
                }

                IssuesActivity.this.mIssueItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeView () {
        this.mActionBar = NamedActionBar.in(this).setTitle("Issues Tracker").setOnBackClickListener(new NamedActionBar.AnimatedBackAction());
    }

    public void onNewIssuesButtonClick(View view) {
        Redirector.from(this).to(CreateNewIssueActivity.class).withAnimation().go();
    }

}
