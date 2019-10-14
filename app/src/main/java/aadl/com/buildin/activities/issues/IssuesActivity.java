package aadl.com.buildin.activities.issues;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import aadl.com.buildin.MainActivity;
import aadl.com.buildin.R;
import aadl.com.buildin.utils.NamedActionBar;
import aadl.com.buildin.utils.Redirector;

public class IssuesActivity extends AppCompatActivity {

    private NamedActionBar mActionBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);


        this.initializeView();
    }

    private void initializeView () {
        this.mActionBar = NamedActionBar.in(this).setTitle("Issues Tracker").setOnBackClickListener(new NamedActionBar.AnimatedBackAction());
    }

    public void onNewIssuesButtonClick(View view) {
        Redirector.from(this).to(CreateNewIssueActivity.class).withAnimation().go();
    }

}
