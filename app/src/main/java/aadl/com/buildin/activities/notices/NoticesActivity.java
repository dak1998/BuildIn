package aadl.com.buildin.activities.notices;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import aadl.com.buildin.R;
import aadl.com.buildin.activities.issues.IssuesActivity;
import aadl.com.buildin.models.IssueItem;
import aadl.com.buildin.models.NoticeItem;
import aadl.com.buildin.utils.NamedActionBar;

public class NoticesActivity extends AppCompatActivity {

    private NamedActionBar mActionBar;
    private List<String> mNoticesList;
    private List<String> mNoticesURLList;
    private ListView mListView;
    private ArrayAdapter<String> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);
        initializeView();

        this.mNoticesList = new ArrayList<>();
        this.mNoticesURLList = new ArrayList<>();

        this.mListView = findViewById(R.id.notices_list);
        this.mListAdapter = new ArrayAdapter<String>(this, R.layout.layout_notice_list,  mNoticesList);
        this.mListView.setAdapter(this.mListAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = NoticesActivity.this.mNoticesURLList.get(i);

                String format = "https://drive.google.com/viewerng/viewer?embedded=true&url=%s";
                String fullPath = String.format(Locale.ENGLISH, format, value);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                startActivity(browserIntent);
            }
        });

        loadData();
    }

    private void loadData() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child("notices").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot s: dataSnapshot.getChildren()) {
                    NoticesActivity.this.mNoticesList.add(s.child("name").getValue(String.class));
                    NoticesActivity.this.mNoticesURLList.add(s.child("url").getValue(String.class));
                }


                NoticesActivity.this.mListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void initializeView () {
        this.mActionBar = NamedActionBar.in(this).setTitle("Important Notices").setOnBackClickListener(new NamedActionBar.AnimatedBackAction());
    }

}
