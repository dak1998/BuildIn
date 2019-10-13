package aadl.com.buildin.activities.issues;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import aadl.com.buildin.R;
import aadl.com.buildin.utils.NamedActionBar;

public class CreateNewIssueActivity extends AppCompatActivity {

    private NamedActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_issue);

        this.initializeView();
    }

    private void initializeView () {
        this.mActionBar = NamedActionBar.in(this).setTitle("Create a new Issue").setOnBackClickListener(new NamedActionBar.AnimatedBackAction());
    }
}
